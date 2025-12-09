package se.chasacademy.databaser.v5.boilerroom.Repositories;

import java.util.List;

import se.chasacademy.databaser.v5.boilerroom.Models.Author;
import se.chasacademy.databaser.v5.boilerroom.Models.Book;
import se.chasacademy.databaser.v5.boilerroom.RowMappers.BookRowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepository {
    private JdbcClient jdbcClient;
    private AuthorRepository authorRepository;

    public BookRepository(JdbcClient jdbcClient, AuthorRepository authorRepository) {
        this.jdbcClient = jdbcClient;
        this.authorRepository = authorRepository;
    }

    public List<Book> findAll() {
        return jdbcClient
                .sql("""
                        select b.isbn, b.name as title, b.publication_date, c.category_id, c.name as category, a.name as author from books b
                                join book_author ba on b.isbn = ba.isbn
                                join authors a on ba.author_id = a.author_id
                                join category c on b.category_id = c.category_id
                        """)
                .query(new BookRowMapper())
                .list();
    }
}
