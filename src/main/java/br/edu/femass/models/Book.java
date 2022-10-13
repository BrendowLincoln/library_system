package br.edu.femass.models;

import java.util.List;
import java.util.Objects;

public class Book {
    private Long code;
    private String title;
    private Author author;
    private List<Copy> copies;

    public Book() {  }

    public Book(Long code, String title, List<Copy> copies) {
        this.code = code;
        this.title = title;
        this.copies = copies;
    }

    public Long getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }

    public Author getAuthor() { return this.author; }

    public List<Copy> getCopies() {
        return this.copies;
    }

    @Override
    public String toString() {
        return this.code + " - " + this.title;
    }

     @Override
    public boolean equals(Object object) {
        Book book = (Book) object;
        return this.code.equals(book.code);
    }
}
