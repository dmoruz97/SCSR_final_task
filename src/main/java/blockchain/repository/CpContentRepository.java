package blockchain.repository;

import blockchain.model.CpContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository("cpContentRepository")
public interface CpContentRepository extends JpaRepository<CpContent, Long> {

    CpContent findByIdCpContent(Integer idCpContent);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Transactional
    @Query("UPDATE CpContent c SET c.field1 = :field1, c.field2 = :field2, c.field3 = :field3 WHERE c.idCpContent = :idCpContent")
    void updateByIdCpContent(@Param("idCpContent")Integer idCpContent, @Param("field1")String field1, @Param("field2")String field2, @Param("field3")String field3);

}
