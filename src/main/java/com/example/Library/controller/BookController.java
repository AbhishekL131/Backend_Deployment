package com.example.Library.controller;

import java.util.List;
//import java.util.Optional;
import java.util.Optional;
import java.util.stream.Collectors;

// import org.apache.catalina.User;
import com.example.Library.model.User;

import org.springframework.security.core.Authentication;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.Library.model.Book;
import com.example.Library.service.BookService;
import com.example.Library.service.UserService;




@CrossOrigin(origins="*")
@RestController
@RequestMapping("/books")
public class BookController {


    @Autowired
    private BookService bookService;


    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<List<Book>> getAllBooksOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Optional<User> userOptional = userService.getByUsername(userName);
        if (userOptional.isPresent()) {
            List<Book> books = userOptional.get().getBooks();
            if(!books.isEmpty()){
                return new ResponseEntity<>(books,HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    


    @GetMapping("id/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable ObjectId id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Optional<User> user = userService.getByUsername(userName);
        List<Book> collect = user.isPresent() ? user.get().getBooks().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList()) : java.util.Collections.emptyList();

        if(collect != null){
             Optional<Book> book = bookService.getByID(id);

             if(book.isPresent()){
            return new ResponseEntity<>(book.get(),HttpStatus.OK);
        }
        }
        
       
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        

    }
        


    @PostMapping
    public ResponseEntity<Book> saveNewBook(@RequestBody Book book){
       try{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        bookService.saveBook(book,userName);
        return new ResponseEntity<>(book,HttpStatus.CREATED);
       }catch(Exception e){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> DeleteBook(@PathVariable ObjectId id){
     Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
     String username = authentication.getName();
     Optional<Book> book = bookService.getByID(id);
     if(book.isPresent()){
        bookService.deleteBookById(id,username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
     }else{
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
     }
    }
   

    

}
