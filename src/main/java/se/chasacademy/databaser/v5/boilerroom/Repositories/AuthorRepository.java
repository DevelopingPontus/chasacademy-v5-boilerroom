package se.chasacademy.databaser.v5.boilerroom.Repositories;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import se.chasacademy.databaser.v5.boilerroom.Models.Author;
import se.chasacademy.databaser.v5.boilerroom.RowMappers.AuthorRowMapper;

import java.util.List;

@Repository
public class AuthorRepository {
    private JdbcClient jdbcClient;

    public AuthorRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    // get by id
    public Author findById(int author_id) {
        return jdbcClient.sql("SELECT * FROM authors WHERE author_id = ?")
                .params(author_id)
                .query(new AuthorRowMapper())
                .single();
    }

    // Hitta alla
    public List<Author> findAll() {
        return jdbcClient.sql("SELECT * FROM authors")
                .query(new AuthorRowMapper())
                .list();
    }

    // skapa ny
    public Author create(String name) {
        return jdbcClient.sql("INSERT INTO authors (name) VALUES (?) RETURNING *")
                .params(name)
                .query(new AuthorRowMapper())
                .single();
    }

    // hämta authors per bok
    public List<Author> findByBook(String isbn) {
        return jdbcClient.sql(
                "SELECT * FROM authors INNER JOIN book_author ON authors.author_id = book_author.author_id WHERE book_author.isbn = ?")
                .params(isbn)
                .query(new AuthorRowMapper())
                .list();
    }
}
