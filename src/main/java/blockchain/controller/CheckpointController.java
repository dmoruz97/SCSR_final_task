package blockchain.controller;

import blockchain.model.Checkpoint;
import blockchain.model.CompleteCheckpoint;
import blockchain.model.CpContent;
import blockchain.model.Pipeline;
import blockchain.service.BlockchainService;
import blockchain.service.CheckpointService;
import blockchain.service.CpContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value="/checkpoint")

public class CheckpointController {

    @Autowired
    private CheckpointService checkpointService;

    @Autowired
    private CpContentService cpContentService;

    @Autowired
    private BlockchainService blockchainService;

    private List<Checkpoint> getAllCheckpoint(HttpSession session){
        Integer id_pipeline = (Integer) session.getAttribute("id_pipeline");
        List<Checkpoint> list = checkpointService.findCheckpointsByPipeline(id_pipeline);   // ordered by Parent

        return list;
    }

    // VIEW CHECKPOINTS
    @RequestMapping(value="/viewTemp", method = RequestMethod.GET)
    public ModelAndView showCheckpointTemp(ModelAndView modelAndView, Model model, HttpSession session, Pipeline pipeline){

        session.setAttribute("id_pipeline", pipeline.getIdPipeline());
        modelAndView.setViewName("checkpoint/view");

        return this.showCheckpoint(modelAndView, model, session);
    }

    @RequestMapping(value="/view", method = RequestMethod.GET)
    public ModelAndView showCheckpoint(ModelAndView modelAndView, Model model, HttpSession session){

        //session.setAttribute("id_pipeline", pipeline.getIdPipeline());
        modelAndView.setViewName("checkpoint/view");

        ArrayList<CompleteCheckpoint> list = new ArrayList<>();

        List<Checkpoint> listTemp = this.getAllCheckpoint(session);
        for (Checkpoint item : listTemp) {
            CpContent cpContent = cpContentService.getCpContentById(item.getContent());

            list.add(new CompleteCheckpoint(item.getIdCheckpoint(), item.getDescription(), item.getTransactionhash(), item.getParent(), item.getPipeline(), item.getContent(), cpContent.getField1(), cpContent.getField2(), cpContent.getField3()));
        }
        model.addAttribute("completecheckpoints", list);

        return modelAndView;
    }

    // ADD CHECKPOINT
    @RequestMapping(value="/add", method = RequestMethod.GET)
    public ModelAndView addCheckpointView(HttpSession session){

        ModelAndView modelAndView = new ModelAndView();
        Checkpoint checkpoint = new Checkpoint();
        CpContent cpContent = new CpContent();

        modelAndView.setViewName("checkpoint/add");
        modelAndView.addObject("checkpoint", checkpoint);
        modelAndView.addObject("cpcontent", cpContent);
        modelAndView.addObject("id_pipeline", session.getAttribute("id_pipeline"));

        return modelAndView;
    }

    @RequestMapping(value="/add", method = RequestMethod.POST)
    public ModelAndView addCheckpoint(@Valid Checkpoint checkpoint, @Valid CpContent content, BindingResult bindingResult, Model model, HttpSession session){

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()){
            modelAndView.addObject("message", "Binding error!");
            modelAndView.setViewName("checkpoint/add");
        }
        else {
            cpContentService.saveCpContent(content);

            // Ottengo id dell'ultimo CpContent salvato nel DB per associarlo al Checkpoint appena creato
            Integer idCpContentJustSaved = cpContentService.getLastSavedIdCpContent();
            checkpoint.setContent(idCpContentJustSaved);

            // query del tipo SELECT id FROM news ORDER by id DESC LIMIT 1
            Integer idLastCheckpoint = checkpointService.getLastCheckpointByIdPipeline(checkpoint.getPipeline());

            if (idLastCheckpoint == 0){
                checkpoint.setParent(null);
            }
            else {
                checkpoint.setParent(idLastCheckpoint);
            }

            CompleteCheckpoint completeCheckpoint = new CompleteCheckpoint(checkpoint.getIdCheckpoint(), checkpoint.getDescription(), checkpoint.getTransactionhash(), checkpoint.getParent(), checkpoint.getPipeline(), checkpoint.getContent(), content.getField1(), content.getField2(), content.getField3());

            String hash = blockchainService.writeOnBlockchain(completeCheckpoint.toString(), "CHECKPOINT");

            if (hash != null) {
                checkpoint.setTransactionhash(hash);
                checkpointService.saveCheckpoint(checkpoint);

                modelAndView.addObject("message", "Checkpoint and Content have been saved successfully");
            }
            else {
                modelAndView.addObject("message", "Checkpoint and Content have NOT been saved successfully [Blockchain]");
            }


            return this.showCheckpoint(modelAndView, model, session);
        }

        return modelAndView;
    }

    // REMOVE CHECKPOINT
    @RequestMapping(value="/remove", method = RequestMethod.POST)
    public ModelAndView removeCheckpoint(ModelAndView modelAndView, Model model, HttpSession session, CompleteCheckpoint completeCheckpoint){

        checkpointService.deleteCheckpointById(completeCheckpoint.getIdCheckpoint());

        return this.showCheckpoint(modelAndView, model, session);
    }

    // EDIT CHECKPOINT
    @RequestMapping(value="/edit", method = RequestMethod.GET)
    public ModelAndView editCheckpointView(CompleteCheckpoint completeCheckpoint){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("checkpoint/edit");
        modelAndView.addObject(completeCheckpoint);

        return modelAndView;
    }

    @RequestMapping(value="/edit", method = RequestMethod.POST)
    public ModelAndView editCheckpoint(@Valid CompleteCheckpoint completeCheckpoint, BindingResult bindingResult, Model model, HttpSession session){

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()){
            modelAndView.addObject("message", "Binding error!");
            modelAndView.setViewName("checkpoint/edit");
        }
        else {
            String hash = blockchainService.writeOnBlockchain(completeCheckpoint.toString(), "CHECKPOINT");

            checkpointService.updateCheckpointById(completeCheckpoint.getIdCheckpoint(), completeCheckpoint.getDescription(), hash, completeCheckpoint.getParent(), completeCheckpoint.getContent());
            cpContentService.updateCpContentById(completeCheckpoint.getContent(), completeCheckpoint.getField1(), completeCheckpoint.getField2(), completeCheckpoint.getField3());

            modelAndView.addObject("message", "Checkpoint has been edited successfully");

            return this.showCheckpoint(modelAndView, model, session);
        }

        return modelAndView;
    }
}
