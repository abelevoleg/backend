package vkudryashov.webserver.service;

import vkudryashov.webserver.model.User;
import java.util.List;

/**
* Service class for {@link User}
 *
 * @author Vladimir Kudryashov
 * version 1.0
* */

public interface UserService {
    void save(User user);
    void delete(User user);
    User findByUsername(String username);
    List<User> findAll();
    void setRoles(User user);
}
