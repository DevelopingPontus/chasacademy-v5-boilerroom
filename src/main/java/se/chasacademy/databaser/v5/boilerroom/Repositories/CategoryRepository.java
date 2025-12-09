package se.chasacademy.databaser.v5.boilerroom.Repositories;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import se.chasacademy.databaser.v5.boilerroom.Models.Category;
import se.chasacademy.databaser.v5.boilerroom.RowMappers.CategoryRowMapper;

import java.util.List;

@Repository
public class CategoryRepository {
    private JdbcClient jdbcClient;

    public CategoryRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    //get by id
    public Category getCategoryByID(int category_id) {
        return jdbcClient.sql("SELECT * FROM category WHERE category_id = ?")
                .param(category_id)
                .query(new CategoryRowMapper())
                .single();
    }

    //Hitta alla kategorier
    public List<Category> findAll() {
        return jdbcClient.sql("SELECT * FROM category")
                .query(new CategoryRowMapper())
                .list();
    }

    //skapa ett kategori
    public Category createCategory(String name) {
        return jdbcClient.sql("INSERT INTO category (name) VALUES (?) RETURNING *")
                .param(name)
                .query(new CategoryRowMapper())
                .single();
    }
}
