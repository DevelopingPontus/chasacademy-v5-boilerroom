package se.chasacademy.databaser.v5.boilerroom.RowMappers;

import org.springframework.jdbc.core.RowMapper;
import se.chasacademy.databaser.v5.boilerroom.Models.Library;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LibraryRowMapper implements RowMapper<Library> {

    @Override
    public Library mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Library(
                rs.getInt("library_id"),
                rs.getString("name")
        );
    }
}
