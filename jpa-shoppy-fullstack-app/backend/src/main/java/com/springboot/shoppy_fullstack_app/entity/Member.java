package com.springboot.shoppy_fullstack_app.entity;

import com.springboot.shoppy_fullstack_app.dto.MemberDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name="member")
@Setter
@Getter
public class Member {
    @Id
    @Column(name="id", length=50)
    private String id;

    @Column(name="pwd", length = 100)
    private String pwd;

    @Column(name="name", length = 20, nullable = false)
    private String name;

    @Column(name="phone", length = 13)
    private String phone;

    @Column(name="email", length = 50)
    private String email;

    @Column(name="mdate")
    private LocalDate mdate;

    //※ 생성자를 반드시 정의!
    public Member() {}
    public Member(MemberDto memberDto) {
        this.id = memberDto.getId();
        this.pwd = memberDto.getPwd();
        this.name = memberDto.getName();
        this.phone = memberDto.getPhone();
        this.email = memberDto.getEmail();
        this.mdate = LocalDate.now();
    }
}

//id	varchar(50)	NO	PRI
//pwd	varchar(100)	NO
//name	varchar(20)	NO
//phone	char(13)	YES
//email	varchar(50)	NO
//mdate	date	YES