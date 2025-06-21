package com.example.Library.service;


import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.Library.repository.BookRepository;
import java.util.List;
import java.util.Optional;

import com.example.Library.model.Book;
import com.example.Library.model.User;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private UserService userService;

    public List<Book> getBooks(){
        return bookRepo.findAll();
    }

    @Transactional
    public void saveBook(Book book,String username){
       try{
         Optional<User> optionalUser = userService.getByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Book saved = bookRepo.save(book);
            user.getBooks().add(saved);
            userService.saveUser(user);
        } else {
            throw new RuntimeException("User not found with username: " + username);
        }
       }catch(Exception e){
         System.out.println(e);
       }
    }

    @Cacheable("Books")
    public Optional<Book> getByID(ObjectId id){
        return bookRepo.findById(id);
    }

    public void deleteBookById(ObjectId id,String userName){
        Optional<User> optionalUser = userService.getByUsername(userName);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.getBooks().removeIf(x -> x.getId().equals(id));
            userService.saveUser(user);
            bookRepo.deleteById(id);
        } else {
            throw new RuntimeException("User not found with username: " + userName);
        }
    }


    public List<Book> getByAuth(String author){
      return bookRepo.getByAuthor(author);
    }

    public List<Book> getBySub(String subject){
        return bookRepo.getBySubject(subject);
    }




}
