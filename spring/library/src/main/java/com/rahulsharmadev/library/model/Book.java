package com.rahulsharmadev.library.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Book {
    @Id
    private String bookId;
    private String name;
    private String author;
    private String description;
    private int mrp;

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setMrp(int mrp) {
        this.mrp = mrp;
    }

    public String getAuthor() {
        return author;
    }

    public String getDescription() {
        return description;
    }

    public int getMrp() {
        return mrp;
    }

    public String getName() {
        return name;
    }

    public String getBookId() {
        return bookId;
    }

}
