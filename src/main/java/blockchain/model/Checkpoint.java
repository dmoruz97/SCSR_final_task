package blockchain.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="checkpoint")
public class Checkpoint {

    @Id
    @Column(name="id_checkpoint")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer idCheckpoint;

    @Column(name="description")
    @NotNull
    @NotEmpty
    @Size(min=4)
    private String description;

    @Column(name = "transactionhash")
    private String transactionhash;

    @Column(name="parent")
    //@NotNull
    private Integer parent;

    @Column(name="pipeline")
    @NotNull
    private Integer pipeline;

    @Column(name="content")
    // @NotNull
    private Integer content;

    public Checkpoint(){ }

    public Checkpoint(Integer id_checkpoint, String description, String transactionhash,
                      Integer parent, Integer pipeline, Integer content){
        this.idCheckpoint = id_checkpoint;
        this.description = description;
        this.transactionhash = transactionhash;
        this.parent = parent;
        this.pipeline = pipeline;
        this.content = content;
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

    public String toString(){
        return "Checkpoint [" + this.idCheckpoint + ", " + this.description + ", " + this.parent + ", " + this.pipeline + ", " + this.getContent().toString() + "]";
    }
}
