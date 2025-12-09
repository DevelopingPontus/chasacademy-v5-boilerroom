package se.chasacademy.databaser.v5.boilerroom.RowMappers;

import org.springframework.jdbc.core.RowMapper;
import se.chasacademy.databaser.v5.boilerroom.Models.Book;
import se.chasacademy.databaser.v5.boilerroom.Models.Category;

public class BookRowMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(java.sql.ResultSet rs, int rowNum) throws java.sql.SQLException {
        String isbn = rs.getString("isbn");
        String title = rs.getString("title");
        java.time.LocalDate publishedDate = rs.getDate("publication_date").toLocalDate();
        Category category = new Category(rs.getInt("category_id"), rs.getString("category"));
        // Författare hanteras separat i BookRepository
        return new Book(isbn, title, publishedDate, category, null);
    }
}
