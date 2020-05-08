package blockchain.model;

import io.reactivex.Flowable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.Utf8String;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tuples.generated.Tuple4;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import org.web3j.tx.gas.ContractGasProvider;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 4.2.0.
 */
public class Report extends Contract {
    private static final String BINARY = "6080604052600280546001600160a01b0319163317905534801561002257600080fd5b506040516106463803806106468339810180604052602081101561004557600080fd5b81019080805164010000000081111561005d57600080fd5b8201602081018481111561007057600080fd5b815164010000000081118282018710171561008a57600080fd5b505080519093506100a492506001915060208401906100af565b50506000805561014a565b828054600181600116156101000203166002900490600052602060002090601f016020900481019282601f106100f057805160ff191683800117855561011d565b8280016001018555821561011d579182015b8281111561011d578251825591602001919060010190610102565b5061012992915061012d565b5090565b61014791905b808211156101295760008155600101610133565b90565b6104ed806101596000396000f3fe608060405234801561001057600080fd5b50600436106100935760003560e01c80638da5cb5b116100665780638da5cb5b146100f65780638ee759ff1461011a5780639499e01814610122578063e21f37ce1461012a578063e4085d67146101a757610093565b80631270a02d1461009857806324197a85146100b75780638401f571146100d457806388da9b36146100dc575b600080fd5b6100b5600480360360208110156100ae57600080fd5b50356101df565b005b6100b5600480360360208110156100cd57600080fd5b50356102bc565b6100b5610397565b6100e46103c0565b60408051918252519081900360200190f35b6100fe6103d3565b604080516001600160a01b039092168252519081900360200190f35b6100e46103e2565b6100b56103e8565b61013261040b565b6040805160208082528351818301528351919283929083019185019080838360005b8381101561016c578181015183820152602001610154565b50505050905090810190601f1680156101995780820380516001836020036101000a031916815260200191505b509250505060405180910390f35b6101af610498565b604080516001600160a01b0390951685526020850193909352838301919091526060830152519081900360800190f35b3360009081526004602052604090205460ff16151560011415610227573360009081526006602090815260408083208054600101905560039091529020805482019055610260565b336000908152600460209081526040808320805460ff191660019081179091558354810184556006835281842055600390915290208190555b6040805182815260208101829052600a81830152600160b21b6910d21150d2d413d25395026060820152905133917fe02fddb04874bc7b66d79324510aed8e1a1d8cc36a5f330c8782619fdc3390f3919081900360800190a250565b3360009081526004602052604090205460ff1615156001141561030457336000908152600560209081526040808320805460010190556003909152902080548201905561033d565b336000908152600460209081526040808320805460ff191660019081179091558354810184556005835281842055600390915290208190555b6040805182815260208101829052600881830152600160c01b67504950454c494e45026060820152905133917fe02fddb04874bc7b66d79324510aed8e1a1d8cc36a5f330c8782619fdc3390f3919081900360800190a250565b336000908152600360209081526040808320839055600582528083208390556006909152812055565b3360009081526003602052604090205490565b6002546001600160a01b031681565b60005490565b6002546001600160a01b0316331415610409576002546001600160a01b0316ff5b565b60018054604080516020600284861615610100026000190190941693909304601f810184900484028201840190925281815292918301828280156104905780601f1061046557610100808354040283529160200191610490565b820191906000526020600020905b81548152906001019060200180831161047357829003601f168201915b505050505081565b33600081815260036020908152604080832054600583528184205460069093529220549091929356fea165627a7a723058208925530605a722dd985ce79175c76dc2d01aa326cba8aa247c3370397fafd0570029";

    public static final String FUNC_ADDCHECKPOINT = "addCheckpoint";

    public static final String FUNC_ADDPIPELINE = "addPipeline";

    public static final String FUNC_RESETREPORT = "resetReport";

    public static final String FUNC_GETETHERSPENT = "getEtherSpent";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_GETCLIENTNUMBER = "getClientNumber";

    public static final String FUNC_DESTRUCTCONTRACT = "destructContract";

    public static final String FUNC_MESSAGE = "message";

    public static final String FUNC_GETREPORT = "getReport";

    public static final Event LOGDEPOSITMADE_EVENT = new Event("LogDepositMade", 
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>(true) {}, new TypeReference<Uint256>() {}, new TypeReference<Utf8String>() {}));
    

    @Deprecated
    protected Report(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Report(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, credentials, contractGasProvider);
    }

    @Deprecated
    protected Report(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    protected Report(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        super(BINARY, contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public RemoteCall<TransactionReceipt> addCheckpoint(BigInteger etherSpent) {
        final Function function = new Function(
                FUNC_ADDCHECKPOINT, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(etherSpent)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> addPipeline(BigInteger etherSpent) {
        final Function function = new Function(
                FUNC_ADDPIPELINE, 
                Arrays.<Type>asList(new org.web3j.abi.datatypes.generated.Uint256(etherSpent)), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> resetReport() {
        final Function function = new Function(
                FUNC_RESETREPORT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<BigInteger> getEtherSpent() {
        final Function function = new Function(FUNC_GETETHERSPENT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<String> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<BigInteger> getClientNumber() {
        final Function function = new Function(FUNC_GETCLIENTNUMBER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function, BigInteger.class);
    }

    public RemoteCall<TransactionReceipt> destructContract() {
        final Function function = new Function(
                FUNC_DESTRUCTCONTRACT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<String> message() {
        final Function function = new Function(FUNC_MESSAGE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Utf8String>() {}));
        return executeRemoteCallSingleValueReturn(function, String.class);
    }

    public RemoteCall<Tuple4<String, BigInteger, BigInteger, BigInteger>> getReport() {
        final Function function = new Function(FUNC_GETREPORT, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
        return new RemoteCall<Tuple4<String, BigInteger, BigInteger, BigInteger>>(
                new Callable<Tuple4<String, BigInteger, BigInteger, BigInteger>>() {
                    @Override
                    public Tuple4<String, BigInteger, BigInteger, BigInteger> call() throws Exception {
                        List<Type> results = executeCallMultipleValueReturn(function);
                        return new Tuple4<String, BigInteger, BigInteger, BigInteger>(
                                (String) results.get(0).getValue(), 
                                (BigInteger) results.get(1).getValue(), 
                                (BigInteger) results.get(2).getValue(), 
                                (BigInteger) results.get(3).getValue());
                    }
                });
    }

    public List<LogDepositMadeEventResponse> getLogDepositMadeEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(LOGDEPOSITMADE_EVENT, transactionReceipt);
        ArrayList<LogDepositMadeEventResponse> responses = new ArrayList<LogDepositMadeEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            LogDepositMadeEventResponse typedResponse = new LogDepositMadeEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.accountAddress = (String) eventValues.getIndexedValues().get(0).getValue();
            typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
            typedResponse.kind = (String) eventValues.getNonIndexedValues().get(1).getValue();
            responses.add(typedResponse);
        }
        return responses;
    }

    public Flowable<LogDepositMadeEventResponse> logDepositMadeEventFlowable(EthFilter filter) {
        return web3j.ethLogFlowable(filter).map(new io.reactivex.functions.Function<Log, LogDepositMadeEventResponse>() {
            @Override
            public LogDepositMadeEventResponse apply(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(LOGDEPOSITMADE_EVENT, log);
                LogDepositMadeEventResponse typedResponse = new LogDepositMadeEventResponse();
                typedResponse.log = log;
                typedResponse.accountAddress = (String) eventValues.getIndexedValues().get(0).getValue();
                typedResponse.amount = (BigInteger) eventValues.getNonIndexedValues().get(0).getValue();
                typedResponse.kind = (String) eventValues.getNonIndexedValues().get(1).getValue();
                return typedResponse;
            }
        });
    }

    public Flowable<LogDepositMadeEventResponse> logDepositMadeEventFlowable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(LOGDEPOSITMADE_EVENT));
        return logDepositMadeEventFlowable(filter);
    }

    @Deprecated
    public static Report load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Report(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    @Deprecated
    public static Report load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Report(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static Report load(String contractAddress, Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider) {
        return new Report(contractAddress, web3j, credentials, contractGasProvider);
    }

    public static Report load(String contractAddress, Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider) {
        return new Report(contractAddress, web3j, transactionManager, contractGasProvider);
    }

    public static RemoteCall<Report> deploy(Web3j web3j, Credentials credentials, ContractGasProvider contractGasProvider, String initMessage) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(initMessage)));
        return deployRemoteCall(Report.class, web3j, credentials, contractGasProvider, BINARY, encodedConstructor);
    }

    public static RemoteCall<Report> deploy(Web3j web3j, TransactionManager transactionManager, ContractGasProvider contractGasProvider, String initMessage) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(initMessage)));
        return deployRemoteCall(Report.class, web3j, transactionManager, contractGasProvider, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Report> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, String initMessage) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(initMessage)));
        return deployRemoteCall(Report.class, web3j, credentials, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    @Deprecated
    public static RemoteCall<Report> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, String initMessage) {
        String encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.<Type>asList(new org.web3j.abi.datatypes.Utf8String(initMessage)));
        return deployRemoteCall(Report.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, encodedConstructor);
    }

    public static class LogDepositMadeEventResponse {
        public Log log;

        public String accountAddress;

        public BigInteger amount;

        public String kind;
    }
}
