package blockchain.service;

import org.springframework.stereotype.Service;
import org.web3j.protocol.Web3j;

@Service("blockchainService")
public interface BlockchainService {

    Web3j getWeb3();

    // METODO DI TEST
    void checkHashFile();

    String writeOnBlockchain(String data, String type);
}
