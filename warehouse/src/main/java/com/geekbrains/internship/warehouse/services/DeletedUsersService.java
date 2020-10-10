package com.geekbrains.internship.warehouse.services;

import com.geekbrains.internship.warehouse.entities.DeletedUser;
import com.geekbrains.internship.warehouse.entities.User;
import com.geekbrains.internship.warehouse.repositories.DeletedUsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DeletedUsersService {
    private DeletedUsersRepository deletedUsersRepository;

    @Autowired
    public void setDeletedUsersRepository(DeletedUsersRepository deletedUsersRepository) {
        this.deletedUsersRepository = deletedUsersRepository;
    }

    public List<DeletedUser> findAll() {
        return (List<DeletedUser>) deletedUsersRepository.findAll();
    }

    public void save(DeletedUser deletedUser) {
        deletedUsersRepository.save(deletedUser);
    }

    public boolean findUserInDeleted(Long id){
        List<DeletedUser> deletedUsers = findAll();
        for (DeletedUser deletedUser : deletedUsers){
            if (deletedUser.getUser().getId() == id)
                return true;
        }
        return false;
    }
}
