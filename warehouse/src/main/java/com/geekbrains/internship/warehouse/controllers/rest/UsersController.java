package com.geekbrains.internship.warehouse.controllers.rest;

import com.geekbrains.internship.warehouse.entities.DeletedUser;
import com.geekbrains.internship.warehouse.entities.User;
import com.geekbrains.internship.warehouse.exceptions.CustomException;
import com.geekbrains.internship.warehouse.services.DeletedUsersService;
import com.geekbrains.internship.warehouse.services.UsersService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/users")
@Api("Set of endpoints for CRUD operations for User")
public class UsersController {
    private UsersService usersService;
    private DeletedUsersService deletedUsersService;

    @Autowired
    public UsersController(UsersService usersService, DeletedUsersService deletedUsersService) {
        this.usersService = usersService;
        this.deletedUsersService = deletedUsersService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    @ApiOperation("Creates a new user")
    public User save(@RequestBody User user) {
        return usersService.restsave(user);
    }

    @GetMapping
    public ResponseEntity<List<User>> read() {
        final List<User> users = usersService.readAll();

        return users != null &&  !users.isEmpty()
                ? new ResponseEntity<>(users, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/actual")
    @ApiOperation("Returns actual users")
    public List<User> getActualUsers() {
        List<User> users = usersService.readAll();
        List<User> actualUsers = new ArrayList<>();
        for (User user : users){
            Long id = user.getId();
            if (!deletedUsersService.findUserInDeleted(id))
                actualUsers.add(user);
        }
        return actualUsers;
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @ApiOperation("Returns one user by id")
    public ResponseEntity<?> read(@PathVariable Long id) {
        final User user = usersService.read(id);
        return user != null
                ? new ResponseEntity<>(user, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@PathVariable(name = "id") Long id, @RequestBody User user) {
        final boolean updated = usersService.update(user, id);

        return updated
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id) {
        final boolean deleted = usersService.delete(id);

        return deleted
                ? new ResponseEntity<>(HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }


}
