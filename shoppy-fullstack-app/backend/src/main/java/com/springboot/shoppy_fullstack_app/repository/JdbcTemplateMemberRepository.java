package com.springboot.shoppy_fullstack_app.repository;

import com.springboot.shoppy_fullstack_app.dto.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class JdbcTemplateMemberRepository implements MemberRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateMemberRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);   //커넥션 생성
    }

    @Override
    public Long findById(String id) {
        String sql = "select count(id) from member where id='?'";
        Long count = jdbcTemplate.queryForObject(sql, Long.class, id);
        return count;
    }

    @Override
    public int save(Member member) {
        String sql = "insert into member(id, pwd, phone, email ,mdate) values (?, ?, ?, ?, ?, now())";
        Object [] param = { member.getId(),
                            member.getPwd(),
                            member.getName(),
                            member.getPhone(),
                            member.getEmail() };

        int rows = jdbcTemplate.update(sql, param);
        return rows;
    }
}
