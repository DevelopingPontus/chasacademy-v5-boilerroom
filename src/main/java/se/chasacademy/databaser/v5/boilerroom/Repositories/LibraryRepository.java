package se.chasacademy.databaser.v5.boilerroom.Repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import se.chasacademy.databaser.v5.boilerroom.Models.Library;
import se.chasacademy.databaser.v5.boilerroom.RowMappers.LibraryRowMapper;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class LibraryRepository {
    private final JdbcTemplate jdbcTemplate;

    public LibraryRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Library create(Library library) {
        String sql = "insert into library (name) values (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, library.getName());
            return ps;
        }, keyHolder);

        int generatedId = keyHolder.getKey().intValue();
        library.setLibraryId(generatedId);

        return library;
    }

    public Library findById(int id) {
        String sql = "select library_id, name from library where library_id = ?";
        return jdbcTemplate.queryForObject(sql, new LibraryRowMapper(), id);
    }

    public List<Library> findAll() {
        String sql = "select library_id, name from library";
        return jdbcTemplate.query(sql, new LibraryRowMapper());
    }

    public void update(Library library) {
        String sql = "update library set name = ? where library_id = ?";
        jdbcTemplate.update(sql, library.getName(), library.getLibraryId());
    }

    public void delete(int id) {
        String sql = "delete from library where library_id = ?";
        jdbcTemplate.update(sql, id);
    }
}
