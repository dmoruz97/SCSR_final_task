package blockchain.service;

import blockchain.model.Checkpoint;
import blockchain.repository.CheckpointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("checkpointService")
public class CheckpointServiceImpl implements CheckpointService {

    @Autowired
    private CheckpointRepository checkpointRepository;

    @Override
    public List<Checkpoint> findCheckpointsByPipeline(Integer id_pipeline) {
        return checkpointRepository.findByPipelineOrderByParentAsc(id_pipeline);
    }

    @Override
    public void saveCheckpoint(Checkpoint checkpoint) {
        checkpointRepository.save(checkpoint);
    }

    @Override
    public void deleteCheckpointById(Integer id_checkpoint) {
        checkpointRepository.deleteByIdCheckpoint(id_checkpoint);
    }

    @Override
    public void updateCheckpointById(Integer id_checkpoint, String description, String transactionhash, Integer parent, Integer content) {
        checkpointRepository.updateByIdCheckpoint(id_checkpoint, description, transactionhash, parent, content);
    }

    @Override
    public List<String> getAllTransactionHash() {
        List<Checkpoint> listCheckpoint = checkpointRepository.findAll();

        ArrayList<String> listhash = new ArrayList<>();

        if (listCheckpoint.size() != 0){
            for (Checkpoint p : listCheckpoint) {
                listhash.add(p.getTransactionhash());
            }
        }

        return listhash;
    }

    @Override
    public Integer getLastCheckpointByIdPipeline(Integer id_pipeline) {

        List<Checkpoint> list = checkpointRepository.findByPipelineOrderByIdCheckpointDesc(id_pipeline);

        if (list.size() > 0) {
            Checkpoint c = list.get(0);

            return c.getIdCheckpoint();
        }
        else {
            return 0;
        }
    }
}
