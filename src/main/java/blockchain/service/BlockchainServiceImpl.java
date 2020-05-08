package blockchain.service;

import blockchain.model.Report;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.RawTransaction;
import org.web3j.crypto.TransactionEncoder;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.response.EthGasPrice;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.Transaction;
import org.web3j.protocol.http.HttpService;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.Contract;
import org.web3j.tx.ManagedTransaction;
import org.web3j.utils.Numeric;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;

@Service("blockchainService")
public class BlockchainServiceImpl implements BlockchainService {

    private Web3j web3;
    public static Report smartContract = null;

    public BlockchainServiceImpl(){
        this.web3 = Web3j.build(new HttpService());  // defaults to http://localhost:8545
        this.initContract();
    }

    // Inizializza lo SmartContract
    private void initContract(){
        try {
            File directory = new File("/Users/davide/ethereum/data/keystore");
            File ftemp = directory.listFiles()[0];

            Credentials c = WalletUtils.loadCredentials("password", ftemp);
            this.smartContract = Report.deploy(web3, c, ManagedTransaction.GAS_PRICE, Contract.GAS_LIMIT, "Report SmartContract").send();

            System.out.println("* SmartContract address: " + smartContract.getContractAddress() + "*");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public Web3j getWeb3(){
        return this.web3;
    }

    // METODO DI TEST
    public void checkHashFile(){
        try {
            File file = new File("/Users/davide/Desktop/fileblockchaintesthash.txt");
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(Files.readAllBytes(Paths.get(file.getPath())));
            byte[] digest = md.digest();
            String myChecksum = DatatypeConverter.printHexBinary(digest).toUpperCase();

            System.out.println("DIGEST 2: " + myChecksum);

            writeOnBlockchain(myChecksum, "");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public String writeOnBlockchain(String data, String type){

        // CREARE JSON(P) dell'oggetto da salvare nella blockchain

        File directorySource = new File("/Users/davide/ethereum/data/keystore");
        File directoryDestination = new File("/Users/davide/ethereum/data/keystore");

        String pathSource = "/Users/davide/ethereum/data/keystore";
        String pathDestination = "/Users/davide/ethereum/data/keystore";

        Credentials credentialsSource = null;
        Credentials credentialsDestination = null;

        String transactionHash = null;

        File ftemp = null;

        try {

            // Recupero info SOURCE
            if (directorySource.listFiles().length == 0) {
                String fileName = WalletUtils.generateNewWalletFile("password", new File(pathSource));

            }
            ftemp = directorySource.listFiles()[0];
            credentialsSource = WalletUtils.loadCredentials("password", ftemp);

            // Recupero info DESTINATION
            if (directoryDestination.listFiles().length == 1) {
                String fileName = WalletUtils.generateNewWalletFile("password", new File(pathDestination));
            }
            ftemp = directoryDestination.listFiles()[1];
            credentialsDestination = WalletUtils.loadCredentials("password", ftemp);

            // Admin admin = Admin.build(new HttpService());
            // admin.personalUnlockAccount(credentialsSource.getAddress(), "password");

            // Request r = admin.personalListAccounts();

            /**
             * Per recuperare address del wallet
             *
             * https://dzone.com/articles/introduction-to-blockchain-with-java-using-ethereu-1
             * https://www.oodlestechnologies.com/blogs/Create-Ethereum-Account-Using-Web3j-And-Java
             * https://www.programcreek.com/java-api-examples/index.php?api=org.web3j.protocol.core.methods.response.EthGasPrice
             */

            // Get NONCE
            EthGetTransactionCount ethGetTransactionCount = web3.ethGetTransactionCount(credentialsSource.getAddress(), DefaultBlockParameterName.LATEST).send();
            BigInteger nonce = ethGetTransactionCount.getTransactionCount();

            // Get GAS_PRICE
            EthGasPrice ethGasPrice = web3.ethGasPrice().send();
            BigInteger gasPrice = ethGasPrice.getGasPrice();

            // Get GAS_LIMIT (default 21000)
            final Integer GASLIMIT = 50000;
            BigInteger gasLimit = BigInteger.valueOf(GASLIMIT);

            // Get DESTINATION ADDRESS
            String destinationAddress = credentialsDestination.getAddress();

            // create transaction
            RawTransaction rawTransaction = RawTransaction.createTransaction(nonce, gasPrice, gasLimit, destinationAddress, data);

            // sign & send  transaction
            byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, credentialsSource);
            String hexValue = Numeric.toHexString(signedMessage);

            EthSendTransaction ethSendTransaction = web3.ethSendRawTransaction(hexValue).sendAsync().get();

            if (ethSendTransaction.hasError()){
                System.out.println("*** NOT SENDED ***");
                System.out.println(ethSendTransaction.getError().getMessage());
            }
            else {
                transactionHash = ethSendTransaction.getTransactionHash();
                System.out.println("HASH: " + transactionHash);

                // SMART CONTRACT DEPLOY
                Transaction t = web3.ethGetTransactionByHash(transactionHash).send().getTransaction().get();

                if (type.equals("PIPELINE")){
                    smartContract.addPipeline(t.getGas()).send();
                }
                else if (type.equals("CHECKPOINT")){
                    smartContract.addCheckpoint(t.getGas()).send();
                }

                Tuple4<String, BigInteger, BigInteger, BigInteger> tuple = smartContract.getReport().send();

                System.out.println("\nContract address: " + smartContract.getContractAddress());
                System.out.println("Owner: " + smartContract.owner().send());
                System.out.println("Client number: " + smartContract.getClientNumber().send());
                System.out.println("Ether spents: " + tuple.getValue2());
                System.out.println("Pipelines created: " + tuple.getValue3());
                System.out.println("Checkpoints created: " + tuple.getValue4());

                // smartContract.destructContract();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return transactionHash;
    }
}
