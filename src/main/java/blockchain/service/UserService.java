package blockchain.service;

import blockchain.model.User;

public interface UserService {

    User findUserByUsername(String username);

    void saveUser(User user);
}
