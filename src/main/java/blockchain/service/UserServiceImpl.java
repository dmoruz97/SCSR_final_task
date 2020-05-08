package blockchain.service;

import blockchain.model.User;
import blockchain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    // private EntityManager entityManager;

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);

        /*CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cQuery = builder.createQuery(User.class);

        Root<User> root = cQuery.from(User.class);
        cQuery
                .select(root)
                .where(builder
                        .like(root.get(username), "%" + username + "%"));

        TypedQuery<User> query = entityManager.createQuery(cQuery);

        return query.getSingleResult();*/
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

}
