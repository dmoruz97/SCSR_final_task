package blockchain.model;

import java.math.BigInteger;

public class TransactionHash {

    private String hash;
    private String from;
    private String to;
    private String input;

    private String blockHash;
    private BigInteger blockNumber;

    private BigInteger gas;

    private BigInteger nonce;

    private BigInteger transactionIndex;

    public TransactionHash() { }

    public TransactionHash(String hash, String from, String to, String input, String blockHash, BigInteger blockNumber, BigInteger gas, BigInteger nonce, BigInteger transactionIndex) {
        this.hash = hash;
        this.from = from;
        this.to = to;
        this.input = input;
        this.blockHash = blockHash;
        this.blockNumber = blockNumber;
        this.gas = gas;
        this.nonce = nonce;
        this.transactionIndex = transactionIndex;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(String blockHash) {
        this.blockHash = blockHash;
    }

    public BigInteger getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(BigInteger blockNumber) {
        this.blockNumber = blockNumber;
    }

    public BigInteger getGas() {
        return gas;
    }

    public void setGas(BigInteger gas) {
        this.gas = gas;
    }

    public BigInteger getNonce() {
        return nonce;
    }

    public void setNonce(BigInteger nonce) {
        this.nonce = nonce;
    }

    public BigInteger getTransactionIndex() {
        return transactionIndex;
    }

    public void setTransactionIndex(BigInteger transactionIndex) {
        this.transactionIndex = transactionIndex;
    }
}
