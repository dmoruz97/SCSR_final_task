package blockchain.repository;

import blockchain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends JpaRepository<User, Long> {

    //@Query("SELECT u.username, u.password FROM User u WHERE u.username=?1")
    User findByUsername(String username);

}
