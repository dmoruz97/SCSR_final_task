package blockchain.controller;

import blockchain.model.Pipeline;
import blockchain.service.BlockchainService;
import blockchain.service.PipelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value="/pipeline")

public class PipelineController {

    @Autowired
    private PipelineService pipelineService;

    @Autowired
    private BlockchainService blockchainService;

    /**********
     https://www.baeldung.com/thymeleaf-list

     https://www.javaboss.it/spring-mvc-controller-parte-2/?doing_wp_cron=1553168847.2485339641571044921875

     --> https://spring.io/guides/tutorials/bookmarks/ <-- @RestController
     https://www.baeldung.com/spring-controllers <-- Da "The MVC Controller" in poi (@ResponseBody)
     https://www.baeldung.com/spring-requestmapping
    ***********/

    private List<Pipeline> getAllPipeline(HttpSession session){
        Integer id = (Integer) session.getAttribute("id_user");
        List<Pipeline> list = pipelineService.findPipelinesByUser(id);

        return list;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView mainPipeline(ModelAndView modelAndView, HttpSession session){

        modelAndView.setViewName("home/dashboard");
        modelAndView.addObject("loggedId", session.getAttribute("id_user"));
        modelAndView.addObject("loggedUsername", session.getAttribute("username"));

        return modelAndView;
    }

    // VIEW PIPELINES
    @RequestMapping(value="/view", method = RequestMethod.GET)
    public ModelAndView showPipeline(ModelAndView modelAndView, Model model, HttpSession session){

        modelAndView.setViewName("pipeline/view");
        model.addAttribute("pipelines", this.getAllPipeline(session));

        return modelAndView;
    }

    // ADD PIPELINE
    @RequestMapping(value="/add", method = RequestMethod.GET)
    public ModelAndView addPipelineView(HttpSession session){

        ModelAndView modelAndView = new ModelAndView();
        Pipeline pipeline = new Pipeline();

        modelAndView.setViewName("pipeline/add");
        modelAndView.addObject("pipeline", pipeline);
        modelAndView.addObject("id_user", session.getAttribute("id_user"));

        return modelAndView;
    }

    @RequestMapping(value="/add", method = RequestMethod.POST)
    public ModelAndView addPipeline(@Valid Pipeline pipeline, BindingResult bindingResult, HttpSession session){

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()){
            modelAndView.addObject("message", "Binding error!");
            modelAndView.setViewName("pipeline/add");
        }
        else {
            String hash = blockchainService.writeOnBlockchain(pipeline.toString(), "PIPELINE");

            if (hash != null) {
                pipeline.setTransactionhash(hash);
                pipelineService.savePipeline(pipeline);

                modelAndView.addObject("message", "Pipeline has been saved successfully");
                modelAndView.setViewName("pipeline/add");
            }
            else {
                modelAndView.addObject("message", "Pipeline has NOT been saved successfully [Blockchain]");
                modelAndView.setViewName("pipeline/add");
            }
        }

        return modelAndView;
    }

    // REMOVE PIPELINE
    @RequestMapping(value="/remove", method = RequestMethod.POST)
    public ModelAndView removePipeline(ModelAndView modelAndView, Model model, HttpSession session, Pipeline pipeline){

        pipelineService.deletePipelineById(pipeline.getIdPipeline());

        model.addAttribute("pipelines", this.getAllPipeline(session));
        modelAndView.setViewName("pipeline/view");

        return modelAndView;
    }

    // EDIT PIPELINE
    @RequestMapping(value="/edit", method = RequestMethod.GET)
    public ModelAndView editPipelineView(Pipeline pipeline){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pipeline/edit");
        modelAndView.addObject(pipeline);

        return modelAndView;
    }

    @RequestMapping(value="/edit", method = RequestMethod.POST)
    public ModelAndView editPipeline(@Valid Pipeline pipeline, BindingResult bindingResult, Model model, HttpSession session){

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()){
            modelAndView.addObject("message", "Binding error!");
            modelAndView.setViewName("pipeline/edit");
        }
        else {
            String hash = blockchainService.writeOnBlockchain(pipeline.toString(), "PIPELINE");

            pipelineService.updatePipelineById(pipeline.getIdPipeline(), pipeline.getDescription(), hash);

            modelAndView.setViewName("pipeline/view");
            modelAndView.addObject("message", "Pipeline has been edited successfully");
            model.addAttribute("pipelines", this.getAllPipeline(session));
        }

        return modelAndView;
    }

}
