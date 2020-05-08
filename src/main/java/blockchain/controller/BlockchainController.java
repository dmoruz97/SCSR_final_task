package blockchain.controller;

import blockchain.model.TransactionHash;
import blockchain.service.BlockchainService;
import blockchain.service.CheckpointService;
import blockchain.service.PipelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Controller
@RequestMapping(value="/blockchain")

public class BlockchainController {

    @Autowired
    private PipelineService pipelineService;

    @Autowired
    private CheckpointService checkpointService;

    @Autowired
    private BlockchainService blockchainService;

    private List<String> getAllTransactionHashOfPipeline(){
        return pipelineService.getAllTransactionHash();
    }

    private List<String> getAllTransactionHashOfCheckpoint(){
        return checkpointService.getAllTransactionHash();
    }

    @RequestMapping(value = "/provaFile")
    public void checkFileHash(){
        try {
            //BlockchainService blockchainService = new BlockchainService();
            // Web3j web3 = blockchainService.getWeb3();

            blockchainService.checkHashFile();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView viewTransactionHash(ModelAndView modelAndView, Model model){

        /**
         * https://github.com/ethereum/wiki/wiki/JSON-RPC#eth_gettransactionbyhash
         */

        try {
            //BlockchainService blockchainService = new BlockchainService();
            Web3j web3 = blockchainService.getWeb3();

            // FROM PIPELINE
            List<TransactionHash> transactionHashListPipeline = new ArrayList<>();

            List<String> listHash = this.getAllTransactionHashOfPipeline();
            for (String hash : listHash) {
                try {
                    Transaction t = web3.ethGetTransactionByHash(hash).send().getTransaction().get();

                    TransactionHash tranHash = new TransactionHash(t.getHash(), t.getFrom(), t.getTo(), t.getInput(), t.getBlockHash(), t.getBlockNumber(), t.getGas(), t.getNonce(), t.getTransactionIndex());
                    transactionHashListPipeline.add(tranHash);
                }
                catch (NoSuchElementException e){
                    e.printStackTrace();
                }
            }

            // FROM CHECKPOINT
            List<TransactionHash> transactionHashListCheckpoint = new ArrayList<>();

            List<String> listHash2 = this.getAllTransactionHashOfCheckpoint();
            for (String hash : listHash2) {
                try {
                    Transaction t = web3.ethGetTransactionByHash(hash).send().getTransaction().get();
                    System.out.println();

                    TransactionHash tranHash = new TransactionHash(t.getHash(), t.getFrom(), t.getTo(), t.getInput(), t.getBlockHash(), t.getBlockNumber(), t.getGas(), t.getNonce(), t.getTransactionIndex());
                    System.out.println();
                    transactionHashListCheckpoint.add(tranHash);
                }
                catch (NoSuchElementException e){
                    // e.printStackTrace();
                    System.out.println("No value present on 'ethGetTransactionByHash'"); //$NON-NLS-1$
                }
            }

            modelAndView.setViewName("blockchain");
            model.addAttribute("transactionsHashesPipeline", transactionHashListPipeline);
            model.addAttribute("transactionsHashesCheckpoint", transactionHashListCheckpoint);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return modelAndView;
    }

}
