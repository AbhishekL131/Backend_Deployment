package com.example.Library.service;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import com.example.Library.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.example.Library.repository.UserRepository;

@Service
@Component
public class UserService {


    @Autowired
    private UserRepository userRepo;

    private static final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    

    public List<User> getAll(){
        return userRepo.findAll();
    }


    public void saveUser(User user){
         userRepo.save(user);
    }

    public void saveNewUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
         userRepo.save(user);
    }

    public void saveAdmin(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER","ADMIN"));
         userRepo.save(user);
    }

    public Optional<User> getById(ObjectId id){
        return userRepo.findById(id);
    }

    public void deleteById(ObjectId id){
        userRepo.deleteById(id);
    }



    public Optional<User> getByUsername(String username){
        return Optional.ofNullable(userRepo.findByUserName(username));
    }
    

}
