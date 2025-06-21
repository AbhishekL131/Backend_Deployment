package com.example.Library.controller;




import org.springframework.security.core.Authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.example.Library.repository.UserRepository;
//import com.example.Library.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

   // @Autowired
   // private UserService userService;


    @Autowired
    private UserRepository userRepo;



    /* @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAll();
        if(!users.isEmpty()){
            return new ResponseEntity<>(users,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    } */

    /*  @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable ObjectId id){
        Optional<User> user = userService.getById(id);
        if(user.isPresent()){
           return new ResponseEntity<>(user.get(),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
        */

   
    @DeleteMapping
    public ResponseEntity<?> deleteByUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userRepo.deleteByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


   
}
