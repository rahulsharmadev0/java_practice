package com.rahulsharmadev.library.repository;

import org.springframework.stereotype.Repository;
import com.rahulsharmadev.library.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
}