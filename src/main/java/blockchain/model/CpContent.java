package blockchain.model;

import javax.persistence.*;

@Entity
@Table(name="cp_content")
public class CpContent {

    @Id
    @Column(name="id_cp_content")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer idCpContent;

    @Column(name="field1")
    //@NotNull
    //@NotEmpty
    private String field1;

    @Column(name="field2")
    //@NotNull
    //@NotEmpty
    private String field2;

    @Column(name="field3")
    //@NotNull
    //@NotEmpty
    private String field3;

    public CpContent(){ }

    public CpContent(Integer id_cp_content, String field1){
        this.idCpContent = id_cp_content;
        this.field1 = field1;
    }

    public CpContent(Integer id_cp_content, String field1, String field2){
        this.idCpContent = id_cp_content;
        this.field1 = field1;
        this.field2 = field2;
    }

    public CpContent(Integer id_cp_content, String field1, String field2, String field3){
        this.idCpContent = id_cp_content;
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
    }

    public Integer getIdCpContent() {
        return idCpContent;
    }

    public void setIdCpContent(Integer idCpContent) {
        this.idCpContent = idCpContent;
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
        return "CpContent [" + this.idCpContent + ", " + this.field1 + ", " + this.field2 + ", " + this.field3 + "]";
    }
}
