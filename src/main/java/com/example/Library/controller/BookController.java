package com.example.Library.controller;

import java.util.List;
//import java.util.Optional;
import java.util.Optional;

// import org.apache.catalina.User;
import com.example.Library.model.User;
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


    @GetMapping("/username/{userName}")
    public ResponseEntity<List<Book>> getAllBooksOfUser(@PathVariable String userName){
        User user = userService.getByUsername(userName);
        List<Book> books = user.getBooks();
        if(!books.isEmpty()){
            return new ResponseEntity<>(books,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }  
    }
    



    @GetMapping("id/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable ObjectId id){
        Optional<Book> book = bookService.getByID(id);
        if(book.isPresent()){
            return new ResponseEntity<>(book.get(),HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
    @PostMapping("/save/{userName}")
    public ResponseEntity<Book> saveNewBook(@RequestBody Book book,@PathVariable String userName){
       try{
        bookService.saveBook(book,userName);
        return new ResponseEntity<>(book,HttpStatus.CREATED);
       }catch(Exception e){
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
       }
    }


    @GetMapping("/author/{author}")
    public ResponseEntity<List<Book>> getbyAuthor(@PathVariable String author){
        List<Book> books = bookService.getByAuth(author);
        if(!books.isEmpty()){
            return new ResponseEntity<>(books,HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/subject/{subject}")
    public ResponseEntity<List<Book>> getBySubject(@PathVariable String subject){
        List<Book> books = bookService.getBySub(subject);
        if(!books.isEmpty()){
            return new ResponseEntity<>(books,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @DeleteMapping("/delete/{userName}/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable ObjectId id,@PathVariable String userName){
        bookService.deleteBookById(id,userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
