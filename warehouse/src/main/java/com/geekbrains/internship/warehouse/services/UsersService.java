package com.geekbrains.internship.warehouse.services;

import com.geekbrains.internship.warehouse.entities.DeletedUser;
import com.geekbrains.internship.warehouse.entities.Product;
import com.geekbrains.internship.warehouse.entities.Role;
import com.geekbrains.internship.warehouse.entities.User;
import com.geekbrains.internship.warehouse.exceptions.CustomException;
import com.geekbrains.internship.warehouse.exceptions.ProductNotFoundException;
import com.geekbrains.internship.warehouse.repositories.DeletedUsersRepository;
import com.geekbrains.internship.warehouse.repositories.RolesRepository;
import com.geekbrains.internship.warehouse.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UsersService implements UserDetailsService {
    private UsersRepository usersRepository;
    private RolesRepository rolesRepository;
    private DeletedUsersRepository deletedUsersRepository;

    @Autowired
    public void setUsersRepository(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Autowired
    public void setDeletedUsersRepository(DeletedUsersRepository deletedUsersRepository) {
        this.deletedUsersRepository = deletedUsersRepository;
    }

    @Autowired
    public void setRolesRepository(RolesRepository rolesRepository) {
        this.rolesRepository = rolesRepository;
    }

    @Autowired
    private PasswordEncoder pwdEncoder;

    @Autowired
    private UserDetailsService userDetailsService;
    public Optional<User> findByLogin(String login) {
        return usersRepository.findOneByLogin(login);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = usersRepository.findOneByLogin(username).orElseThrow(() -> new UsernameNotFoundException("Invalid username or password"));
        User user = usersRepository.findOneByLogin(username).orElseThrow(() -> new CustomException("Invalid username or password",HttpStatus.UNAUTHORIZED));
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Set<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public Optional<User> findLoggedInUser() {
        Authentication userDetails = SecurityContextHolder.getContext().getAuthentication();
        if (!userDetails.isAuthenticated()){
            return Optional.empty();
        }else {
            return findByLogin(userDetails.getName());
        }
    }

    public List<User> findAll() {
        return (List<User>) usersRepository.findAll();
    }

    public void setRoles(User user) {
        Set<Role> roles = new HashSet<>();
        for (String roleName : user.getRoleNames()) {
            roles.add(rolesRepository.findOneByName(roleName));
        }
        user.setRoles(roles);
    }

    public void save(User user) {
//        user.setPassword(pwdEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    public User restsave(User user) {
        user.setPassword(pwdEncoder.encode(user.getPassword()));
        return usersRepository.save(user);
    }

    /**
     * Возвращает список имеющихся пользователей
     * @return список пользователей
     */
    public List<User> readAll(){
        return (List<User>) usersRepository.findAll();
    }

    public User read(Long id){
        return usersRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Can't found provider with id = " + id));
    }

    public boolean update(User user, Long id) {
        if (usersRepository.existsById(id)) {
            user.setPassword(pwdEncoder.encode(user.getPassword()));
            usersRepository.save(user);
            return true;
        }

        return false;
    }

    public boolean delete(Long id) {
        if (usersRepository.existsById(id)) {
            DeletedUser deletedUser = new DeletedUser();
            User user = usersRepository.findById(id).get();
            deletedUser.setUser(user);
            deletedUsersRepository.save(deletedUser);
            return true;
        }
        return false;
    }



    public String currentUserFullname(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object obj = auth.getPrincipal();
        String username = "";
        if (obj instanceof UserDetails) {
            username = ((UserDetails) obj).getUsername();
        } else {
            username = obj.toString();
        }
        Optional<User> userOpt = findByLogin(username);
        if (userOpt.isPresent()){
            User user = userOpt.get();
            String fullname = user.getLastname();
            return fullname;
        } else return null;
    }

    public User findById(Long id) {
        return usersRepository.findById(id).orElseThrow(() -> new ProductNotFoundException("Can't found product with id = " + id));
    }
}