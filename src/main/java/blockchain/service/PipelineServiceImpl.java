package blockchain.service;

import blockchain.model.Pipeline;
import blockchain.repository.PipelineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("pipelineService")
public class PipelineServiceImpl implements PipelineService {

    @Autowired
    private PipelineRepository pipelineRepository;

    @Override
    public List<Pipeline> findAllPipelines(){
        return pipelineRepository.findAll();
    }

    @Override
    public Pipeline findPipelineById(Integer id_pipeline) {
        return pipelineRepository.findByIdPipeline(id_pipeline);
    }

    @Override
    public List<Pipeline> findPipelinesByUser(Integer user) {
        return pipelineRepository.findByUser(user);
    }

    @Override
    public void savePipeline(Pipeline pipeline) {
        pipelineRepository.save(pipeline);
    }

    @Override
    public void deletePipelineById(Integer id_pipeline){
        pipelineRepository.deleteByIdPipeline(id_pipeline);
    }

    @Override
    public void updatePipelineById(Integer id_pipeline, String description, String transactionhash){
        pipelineRepository.updateByIdPipeline(id_pipeline, description, transactionhash);
    }

    @Override
    public List<String> getAllTransactionHash() {

        List<Pipeline> listPipeline = pipelineRepository.findAll();

        ArrayList<String> listhash = new ArrayList<>();

        if (listPipeline.size() != 0){
            for (Pipeline p : listPipeline) {
                listhash.add(p.getTransactionhash());
            }
        }

        return listhash;
    }
}
