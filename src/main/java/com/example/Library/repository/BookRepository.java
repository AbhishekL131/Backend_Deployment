package com.example.Library.repository;

import java.util.List;

import org.bson.types.ObjectId;
//import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.Library.model.Book;


@Repository
public interface BookRepository extends MongoRepository<Book,ObjectId>{

    List<Book> getByAuthor(String author);
    List<Book> getBySubject(String subject);
    
}
