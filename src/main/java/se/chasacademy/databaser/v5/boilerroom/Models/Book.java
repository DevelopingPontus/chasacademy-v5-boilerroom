package se.chasacademy.databaser.v5.boilerroom.Models;

import se.chasacademy.databaser.v5.boilerroom.Models.Author;
import se.chasacademy.databaser.v5.boilerroom.Models.Category;

import java.time.LocalDate;
import java.util.List;

public class Book {
    private String isbn;
    private String title;
    private LocalDate publishedDate;
    private Category category;
    private List<Author> authors;

    public Book(String isbn, String title, LocalDate publishedDate, Category category, List<Author> authors) {
        this.isbn = isbn;
        this.title = title;
        this.publishedDate = publishedDate;
        this.category = category;
        this.authors = authors;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        if (authors.size() > 3) {
            return "Book [isbn=" + isbn + ", title=" + title + ", publishedDate=" + publishedDate + ", category="
                    + category.getName()
                    + ", authors=" + authors.get(0) + ", " + authors.get(1) + ", " + authors.get(2) + " et al.]";
        } else {
            return "Book [isbn=" + isbn + ", title=" + title + ", publishedDate=" + publishedDate + ", category="
                    + category.getName()
                    + ", authors=" + authors + "]";
        }
    }

}
