package com.rahulsharmadev.library.controller;

import java.util.*;
import com.rahulsharmadev.library.model.Book;
import com.rahulsharmadev.library.repository.BookRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookRepository db;

    @GetMapping("/all")
    List<Book> getBooks() {
        return db.findAll();
    }

    @GetMapping("/{bookId}")
    Book getBookById(@PathVariable String bookId) {
        return db.findById(bookId).get();
    }

    @PostMapping
    boolean addBook(@RequestBody Book book) {
        if (db.existsById(book.getBookId())) {
            return false;
        }
        db.save(book);
        return true;
    }

    @PutMapping
    boolean updateBook(@RequestBody Book book) {
        if (db.existsById(book.getBookId())) {
            db.save(book);
            return true;
        }
        return false;
    }

    @DeleteMapping("/{bookId}")
    boolean deleteBook(@PathVariable String bookId) {
        db.deleteById(bookId);
        return !db.existsById(bookId);
    }

}
