package blockchain.model;

public class CompleteCheckpoint {

    private Integer idCheckpoint;
    private String description;
    private String transactionhash;
    private Integer parent;
    private Integer pipeline;
    private Integer content;

    private String field1;
    private String field2;
    private String field3;


    public CompleteCheckpoint(){ }

    public CompleteCheckpoint(Integer idCheckpoint, String description, String transactionhash, Integer parent, Integer pipeline, Integer content, String field1, String field2, String field3){
        this.idCheckpoint = idCheckpoint;
        this.description = description;
        this.transactionhash = transactionhash;
        this.parent = parent;
        this.pipeline = pipeline;
        this.content = content;

        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
    }

    public Integer getIdCheckpoint() {
        return idCheckpoint;
    }

    public void setIdCheckpoint(Integer idCheckpoint) {
        this.idCheckpoint = idCheckpoint;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTransactionhash() {
        return transactionhash;
    }

    public void setTransactionhash(String transactionhash) {
        this.transactionhash = transactionhash;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public Integer getPipeline() {
        return pipeline;
    }

    public void setPipeline(Integer pipeline) {
        this.pipeline = pipeline;
    }

    public Integer getContent() {
        return content;
    }

    public void setContent(Integer content) {
        this.content = content;
    }

    public String getField1() {
        return field1;
    }

    public void setField1(String field1) {
        this.field1 = field1;
    }

    public String getField2() {
        return field2;
    }

    public void setField2(String field2) {
        this.field2 = field2;
    }

    public String getField3() {
        return field3;
    }

    public void setField3(String field3) {
        this.field3 = field3;
    }

    public String toString(){
        return "CompleteCheckpoint [" + this.idCheckpoint + ", " + this.description + ", " + this.transactionhash + ", " + this.parent + ", " + this.pipeline + ", " + this.getContent().toString() + "]";
    }
}
