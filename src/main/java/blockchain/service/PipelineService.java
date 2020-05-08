package blockchain.service;

import blockchain.model.Pipeline;

import java.util.List;

public interface PipelineService {

    List<Pipeline> findAllPipelines();

    Pipeline findPipelineById(Integer id_pipeline);

    List<Pipeline> findPipelinesByUser(Integer user);

    void savePipeline(Pipeline pipeline);

    void deletePipelineById(Integer id_pipeline);

    void updatePipelineById(Integer id_pipeline, String description, String transactionhash);

    List<String> getAllTransactionHash();
}
