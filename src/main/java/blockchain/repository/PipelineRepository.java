package blockchain.repository;

import blockchain.model.Pipeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;


@Repository("pipelineRepository")
public interface PipelineRepository extends JpaRepository<Pipeline, Long> {

    Pipeline findByIdPipeline(Integer id_pipeline);

    List<Pipeline> findByUser(Integer user);

    @Transactional
    void deleteByIdPipeline(Integer id_pipeline);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Transactional
    @Query("UPDATE Pipeline p SET p.description = :description, p.transactionhash = :transactionhash WHERE p.idPipeline = :idPipeline")
    void updateByIdPipeline(@Param("idPipeline")Integer id_pipeline, @Param("description")String description, @Param("transactionhash")String transactionhash);

}
