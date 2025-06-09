package com.example.Library.service;


import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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

    public void saveBook(Book book,String username){
        User user = userService.getByUsername(username);
        Book saved = bookRepo.save(book);
        user.getBooks().add(saved);
        userService.saveUser(user);
        
    }

    @Cacheable("Books")
    public Optional<Book> getByID(ObjectId id){
        return bookRepo.findById(id);
    }

    public void deleteBookById(ObjectId id,String userName){
        User user = userService.getByUsername(userName);
        user.getBooks().removeIf(x -> x.getId().equals(id));
        userService.saveUser(user);
        bookRepo.deleteById(id);
    }


    public List<Book> getByAuth(String author){
      return bookRepo.getByAuthor(author);
    }

    public List<Book> getBySub(String subject){
        return bookRepo.getBySubject(subject);
    }

}
