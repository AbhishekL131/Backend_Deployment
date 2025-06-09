package com.example.Library.model;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.mongodb.lang.NonNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Document(collection="books")
public class Book {


    @Id
    private ObjectId id;
    @NonNull
    private String title;
    private String author;
    private String subject;
}
