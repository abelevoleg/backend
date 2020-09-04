package vkudryashov.webserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;
import vkudryashov.webserver.model.Role;
import vkudryashov.webserver.model.User;

import java.util.HashSet;
import java.util.Set;

@Service
public class DataLoader implements ApplicationRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userService.findByUsername("Administrator") == null){
            User user = new User();
            user.setUsername("Administrator");
            user.setPassword(user.getUsername());
            user.setConfirmPassword(user.getUsername());
            Set<Role> roles = new HashSet<>();
            roles.add(roleService.findByName("ROLE_USER"));
            roles.add(roleService.findByName("ROLE_ADMIN"));
            user.setRoles(roles);
            userService.save(user);
        }
    }
}

