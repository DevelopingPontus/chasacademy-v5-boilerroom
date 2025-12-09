package se.chasacademy.databaser.v5.boilerroom.Rowmappers;

import org.springframework.jdbc.core.RowMapper;
import se.chasacademy.databaser.v5.boilerroom.Models.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthorRowMapper implements RowMapper<Author> {
    @Override
    public Author mapRow (ResultSet rs, int rowNum) throws SQLException {
        Author a = new Author(rs.getInt("Author_id"),rs.getString("name"));
        rs.getString("name");
        rs.getInt("Author_id");
        return a;
    }

}
