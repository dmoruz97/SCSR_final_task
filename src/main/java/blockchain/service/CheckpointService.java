package blockchain.service;

import blockchain.model.Checkpoint;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("checkpointService")
public interface CheckpointService {

    List<Checkpoint> findCheckpointsByPipeline(Integer id_pipeline);

    void saveCheckpoint(Checkpoint checkpoint);

    void deleteCheckpointById(Integer id_checkpoint);

    void updateCheckpointById(Integer id_checkpoint, String description, String transactionhash, Integer parent, Integer content);

    List<String> getAllTransactionHash();

    Integer getLastCheckpointByIdPipeline(Integer id_pipeline);
}
