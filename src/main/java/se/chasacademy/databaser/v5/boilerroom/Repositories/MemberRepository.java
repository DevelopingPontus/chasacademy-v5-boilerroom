package se.chasacademy.databaser.v5.boilerroom.Repositories;

import org.springframework.jdbc.core.simple.JdbcClient;
import se.chasacademy.databaser.v5.boilerroom.Models.Member;
import se.chasacademy.databaser.v5.boilerroom.RowMappers.MemberRowMapper;

import java.util.List;
import java.util.Optional;

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


}
