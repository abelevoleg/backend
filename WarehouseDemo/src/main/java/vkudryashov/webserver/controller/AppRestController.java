package vkudryashov.webserver.controller;

import vkudryashov.webserver.model.RestUser;
import vkudryashov.webserver.model.User;
import vkudryashov.webserver.service.SecurityService;
import vkudryashov.webserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AppRestController {
    @Autowired
    UserService userService;

    @Autowired
    SecurityService securityService;

    @RequestMapping(value = "/rest/csrf", method = RequestMethod.GET)
    public CsrfToken csrf(CsrfToken token) {
        System.out.println(token);
        return token;
    }

    @GetMapping("/rest/users/all")
    public List<RestUser> restUsersGetAll() {
        List<RestUser> restUsers = new ArrayList<>();
        for (User user :userService.getAllUsers()) {
            restUsers.add(new RestUser(user));
        }
        return restUsers;
    }

    @RequestMapping(value = "/rest/users", method = RequestMethod.GET)
    public RestUser restUsersGetUser(@RequestParam(value = "username") String username) {
        return new RestUser(userService.findByUsername(username));
    }

    @RequestMapping(value = "/rest/users", method = RequestMethod.POST)
    public RestUser restUsersAddUser(@RequestParam String username,
                                     @RequestParam String password){
        User user = userService.findByUsername(username);
        if (user != null) throw new ResponseStatusException(HttpStatus.CONFLICT,"Such username is already exists");
        user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userService.save(user);
        return new RestUser(userService.findByUsername(username));
    }

    @RequestMapping(value = "/rest/users", method = RequestMethod.PUT)
    public RestUser restUsersEditUser(@RequestParam String username,
                                      @RequestParam String password){
        User user = userService.findByUsername(username);
        if (user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Username not found");
        user.setUsername(username);
        user.setPassword(password);
        userService.save(user);
        return new RestUser(userService.findByUsername(username));
    }

    @RequestMapping(value = "/rest/users", method = RequestMethod.DELETE)
    public String restUsersDeleteUser(@RequestParam String username){
        User user = userService.findByUsername(username);
        if (user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Username not found");
        userService.delete(user);
        if (userService.findByUsername(username) == null) return String.format("User %s Successfully deleted",username);
        else throw new ResponseStatusException(HttpStatus.NOT_MODIFIED,String.format("User %s has not been deleted",user));
    }
}
