package blockchain.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="pipeline")
public class Pipeline {

    @Id
    @Column(name="id_pipeline")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer idPipeline;

    @Column(name="description")
    @NotNull
    @NotEmpty(message = "- Description must not be empty!")
    @Size(min=4, message = "- Description size must be at least 4 characters")
    private String description;

    @Column(name = "transactionhash")
    private String transactionhash;

    @Column(name="user")
    @NotNull
    private Integer user;

    public Pipeline(){ }

    public Pipeline(Integer id_pipeline, String description, String transactionhash, Integer user){
        this.idPipeline = id_pipeline;
        this.description = description;
        this.transactionhash = transactionhash;
        this.user = user;
    }

    public Integer getIdPipeline() {
        return idPipeline;
    }

    public void setIdPipeline(Integer idPipeline) {
        this.idPipeline = idPipeline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public String getTransactionhash() {
        return transactionhash;
    }

    public void setTransactionhash(String transactionhash) {
        this.transactionhash = transactionhash;
    }

    public String toString(){
        return "Pipeline [" + this.idPipeline + ", " + this.description + ", " + this.user + "]";
    }
}
