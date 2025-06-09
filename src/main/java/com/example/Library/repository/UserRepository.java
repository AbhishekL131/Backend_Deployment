package com.example.Library.repository;



import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.Library.model.User;


@Repository
public interface UserRepository extends MongoRepository<User,ObjectId> {

    User findByUserName(String userName);
    
}
