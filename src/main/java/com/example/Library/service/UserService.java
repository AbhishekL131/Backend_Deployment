package com.example.Library.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.example.Library.model.User;
import java.util.List;
import java.util.Optional;

import com.example.Library.repository.UserRepository;

@Service
@Component
public class UserService {


    @Autowired
    private UserRepository userRepo;
    

    public List<User> getAll(){
        return userRepo.findAll();
    }


    public User saveUser(User user){
        return userRepo.save(user);
    }

    public Optional<User> getById(ObjectId id){
        return userRepo.findById(id);
    }

    public void deleteById(ObjectId id){
        userRepo.deleteById(id);
    }

    public User getByUsername(String username){
        return userRepo.findByUserName(username);
    }
    

}
