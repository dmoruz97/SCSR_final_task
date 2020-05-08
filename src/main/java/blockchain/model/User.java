package blockchain.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="users")
public class User {

    @Id
    @Column(name="id_user")
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer idUser;

    @Column(name="username")
    @NotNull
    @NotEmpty(message = "- Username must not be empty!")
    @Size(min=4, max=50, message = "- Username size must be at least 4 characters (maximum 50)")
    private String username;

    @Column(name="password")
    @NotNull
    @NotEmpty(message = "- Password must not be empty!")
    @Size(min=8, message = "- Password size must be at least 8 characters")
    private String password;

    public User(){ }

    public User(int id, String username, String password){
        this.idUser = id;
        this.username = username;
        this.password = password;
    }

    public Integer getId_user() {
        return idUser;
    }

    public void setId_user(Integer id_user) {
        this.idUser = id_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String toString(){
        return "User [" + this.idUser + ", " + this.username + ", " + this.password + "]";
    }
}
