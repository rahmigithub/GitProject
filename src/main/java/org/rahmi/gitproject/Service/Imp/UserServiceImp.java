package org.rahmi.gitproject.Service.Imp;

import org.rahmi.gitproject.Entity.User;
import org.rahmi.gitproject.Repository.UserRepository;
import org.rahmi.gitproject.Service.IUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements IUserService {

    private final UserRepository userRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImp.class);

    public UserServiceImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void saveUser(User user) {
        try {
            logger.info("Saving user: {}", user.toString());
            userRepository.save(user);
        } catch (Exception e) {
            logger.error("Error saving user: {} Error: {}", user, e.getMessage());
        }
    }

    @Override
    public List<User> findAllUsers() {
        logger.info("Finding all users");
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        logger.info("Finding user by id: {}", id);
        return userRepository.findById(id).get();
    }

    @Override
    public User findByName(String userName) {
        logger.info("Finding user by name: {}", userName);
        return userRepository.findByName(userName);
    }

}
