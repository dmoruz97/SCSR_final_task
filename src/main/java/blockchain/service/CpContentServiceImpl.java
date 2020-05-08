package blockchain.service;

import blockchain.model.CpContent;
import blockchain.repository.CpContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("cpContentService")
public class CpContentServiceImpl implements CpContentService {

    @Autowired
    private CpContentRepository cpContentRepository;

    @Override
    public void saveCpContent(CpContent cpContent) {
        cpContentRepository.save(cpContent);
    }

    @Override
    public Integer getLastSavedIdCpContent() {
        List<CpContent> list = cpContentRepository.findAll();

        if (list != null){
            return (list.get(list.size()-1)).getIdCpContent();
        }
        else {
            return -1;
        }
    }

    @Override
    public CpContent getCpContentById(Integer idCpContent) {
        return cpContentRepository.findByIdCpContent(idCpContent);
    }

    @Override
    public void updateCpContentById(Integer idCpContent, String field1, String field2, String field3) {
        cpContentRepository.updateByIdCpContent(idCpContent, field1, field2, field3);
    }

}
