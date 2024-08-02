package org.rahmi.gitproject.Service;

import org.rahmi.gitproject.Entity.User;
import java.util.List;

public interface IUserService {

    List<User> findAllUsers();

    User findById(Long id);

    void saveUser(User user);

    User findByName(String userName);

}
