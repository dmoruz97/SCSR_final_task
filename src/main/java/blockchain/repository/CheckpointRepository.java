package blockchain.repository;

import blockchain.model.Checkpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository("checkpointRepository")
public interface CheckpointRepository extends JpaRepository<Checkpoint, Long> {

    List<Checkpoint> findByPipelineOrderByParentAsc(Integer id_pipeline);

    @Transactional
    void deleteByIdCheckpoint(Integer id_checkpoint);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Transactional
    @Query("UPDATE Checkpoint c SET c.description = :description, c.transactionhash = :transactionhash, c.parent = :parent, c.content = :content WHERE c.idCheckpoint = :idCheckpoint")
    void updateByIdCheckpoint(@Param("idCheckpoint")Integer id_checkpoint, @Param("description")String description, @Param("transactionhash")String transactionhash, @Param("parent")Integer parent, @Param("content")Integer content);

    List<Checkpoint> findByPipelineOrderByIdCheckpointDesc(Integer id_pipeline);
}


