package com.sokol.library.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Book {

    private int id;
    @NotEmpty
    @Size(min = 1, max = 255, message = "Title must be between 1 and 255 characters long")
    private String title;
    @NotEmpty
    @Size(min = 1, max = 255, message = "Author must be between 1 and 255 characters long")
    private String author;
    @NotEmpty
    @Pattern(regexp = "^\\d{4}$", message = "Year of birth must be a four digit")
    private String year;
    private Integer person_id;

    public Book() {
    }

    public Book(int id, String title, String author, String year, Integer person_id) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.person_id = person_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Integer person_id) {
        this.person_id = person_id;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year='" + year + '\'' +
                ", person_id=" + person_id +
                '}';
    }
}
