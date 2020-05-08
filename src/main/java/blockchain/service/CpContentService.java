package blockchain.service;

import blockchain.model.CpContent;
import org.springframework.stereotype.Service;

@Service("cpContentService")
public interface CpContentService {

    void saveCpContent(CpContent cpContent);

    Integer getLastSavedIdCpContent();

    CpContent getCpContentById(Integer idCpContent);

    void updateCpContentById(Integer idCpContent, String field1, String field2, String field3);

}
