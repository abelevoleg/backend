package vkudryashov.webserver.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;
import vkudryashov.webserver.dao.RoleDao;
import vkudryashov.webserver.dao.UserDao;
import vkudryashov.webserver.model.Role;
import vkudryashov.webserver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of {@link UserService} interface
 *
 * @author Vladimir Kudryashov
 * version 1.0
 * */

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PasswordEncoder pwdEncoder;

    @Override
    public void save(User user) {
        if (user.getConfirmPassword() != null){
            if (user.getConfirmPassword().equals(user.getPassword())) {
                user.setPassword(pwdEncoder.encode(user.getPassword()));
            }else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Значения полей password и confirmPassword должны быть идентичны");
            }
        }
        userDao.save(user);
    }

    @Override
    public void delete(User user) {
        userDao.delete(user);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public void setRoles(User user) {
        Set<Role> roles = new HashSet<>();
        for (String roleName : user.getRoleNames()) {
            roles.add(roleDao.findByName(roleName));
        }
        user.setRoles(roles);
    }
}