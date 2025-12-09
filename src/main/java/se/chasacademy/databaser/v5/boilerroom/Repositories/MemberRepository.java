package se.chasacademy.databaser.v5.boilerroom.Repositories;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import se.chasacademy.databaser.v5.boilerroom.Models.Member;
import se.chasacademy.databaser.v5.boilerroom.RowMappers.MemberRowMapper;

import java.util.List;
import java.util.Optional;

@Repository
public class MemberRepository {
    private JdbcClient jdbcClient;

    public MemberRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Member> findAll() {
        return jdbcClient.sql("select * from members").query(new MemberRowMapper()).list();
    }

    public Optional<Member> findById(int id) {
        try {
            return Optional.of(jdbcClient.sql("select * from members where member_id = ?")
                    .param(id)
                    .query(new MemberRowMapper())
                    .single());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    /**
     * Create a new member in the database
     * 
     * @param name    The name of the member
     * @param address The address of the member
     * @param email   The email of the member
     * @throws IllegalArgumentException if any of the parameters are blank or null
     * @return true if the member was created, false otherwise
     */
    public boolean create(String name, String address, String email) {
        if (name == null || address == null || email == null) {
            throw new IllegalArgumentException("Name, email and address cannot be null");
        }
        if (name.isBlank() || address.isBlank() || email.isBlank()) {
            throw new IllegalArgumentException("Name, email and address cannot be blank");
        }

        int rowsAffected = jdbcClient.sql("insert into members (name, address, email) values (?, ?, ?)")
                .param(name)
                .param(address)
                .param(email)
                .update();
        return rowsAffected == 1;
    }

}
