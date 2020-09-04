package vkudryashov.webserver.controller.rest;

import org.springframework.context.MessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import vkudryashov.webserver.model.Role;
import vkudryashov.webserver.model.User;
import vkudryashov.webserver.service.RoleService;
import vkudryashov.webserver.service.SecurityService;
import vkudryashov.webserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import vkudryashov.webserver.validator.UserValidator;

import java.util.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    UserValidator userValidator;

    @Autowired
    MessageSource messageSource;

    @GetMapping("/rest/users/all")
    public List<User> restUsersGetAll() {
        return userService.findAll();
    }

    @GetMapping(value = "/rest/user/{username}")
    public User restUsersGetUser(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    @PostMapping(value = "/rest/users",produces = "application/json")
    public User restUsersAddUser(@ModelAttribute("userForm") User userForm,
                                 BindingResult bindingResult){
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()){
            List<ObjectError> errors = bindingResult.getAllErrors();
            Set<String> messages = new HashSet<>();
            for (ObjectError objectError :errors) {
                if (objectError.getCode() != null)
                    messages.add(messageSource.getMessage(objectError.getCode(),null,Locale.forLanguageTag("ru_RU")));
            }
            String message = String.join("; ", messages.toArray(new String[0]));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,message);
        }
        userService.setRoles(userForm);
        userService.save(userForm);
        return userForm;
    }

    @PutMapping(value = "/rest/users")
    public User restUsersEditUser(@ModelAttribute("userForm") User userForm,
                                  BindingResult bindingResult){
        User user = userService.findByUsername(userForm.getUsername());
        if (user == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Пользователь с таким значением username не найден");
        Set<String> skip = new HashSet<>();
        skip.add("username");
        if (userForm.getPassword() == null || userForm.getPassword().equals("")){
            skip.add("password");
            userForm.setPassword(user.getPassword());
            userForm.setConfirmPassword(null);
        }
        if (userForm.getFullname() == null){
            userForm.setFullname(user.getFullname());
        }
        if (userForm.getEmail() == null){
            userForm.setEmail(user.getEmail());
        }
        if (userForm.getPhone() == null){
            userForm.setPhone(user.getPhone());
        }
        if (userForm.getRoleNames() == null || userForm.getRoleNames().length == 0){
            Set<String> roleNames = new HashSet<>();
            for (Role role :user.getRoles()) {
                roleNames.add(role.getName());
            }
            userForm.setRoleNames(roleNames.toArray(new String[0]));
        }
        userValidator.setSkip(skip);
        userValidator.validate(userForm, bindingResult);

        /* Имеем ли мы права на редактирование профиля пользователя: можно редактировать либо свой, либо быть админом */
        if (!securityService.findLoggedInUser().getName().equals(user.getUsername())
                && !securityService.findLoggedInUser().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"You do not have permission to change another account");
        }
        if (bindingResult.hasErrors()){
            List<ObjectError> errors = bindingResult.getAllErrors();
            Set<String> messages = new HashSet<>();
            for (ObjectError objectError :errors) {
                if (objectError.getCode() != null)
                    messages.add(messageSource.getMessage(objectError.getCode(),null,Locale.forLanguageTag("ru_RU")));
            }
            String message = String.join("; ", messages.toArray(new String[0]));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,message);
        }
        userService.setRoles(userForm);
        userForm.setId(user.getId());
        userService.save(userForm);
        return userForm;
    }

    @DeleteMapping(value = "/rest/users",produces = "application/json")
    public String restUsersDeleteUser(@RequestParam String username){
        if (username.equals("Administrator")){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"Этого пользователя удалять запрещено");
        }
        if (!securityService.findLoggedInUser().getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,"You do not have permission to change another account");
        }
        User user = userService.findByUsername(username);
        if(user == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Username not found");
        }
        long id = user.getId();
        userService.delete(user);
        if (userService.findByUsername(username) == null) {
            return String.format("{\"id\": %d, \"username\": \"%s\",\"deleted\": true}", id, username);
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("User %s has not been deleted", user));
        }
    }
}
