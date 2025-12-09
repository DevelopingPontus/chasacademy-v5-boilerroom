package se.chasacademy.databaser.v5.boilerroom.RowMappers;

import org.springframework.jdbc.core.RowMapper;
import se.chasacademy.databaser.v5.boilerroom.Models.Category;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryRowMapper implements RowMapper<Category> {

    @Override
    public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
        Category c = new Category(rs.getInt("category_Id"), rs.getString("name"));
        c.setCategory_Id(rs.getInt("category_id"));
        c.setName(rs.getString("name"));
        return c;
    }
}
