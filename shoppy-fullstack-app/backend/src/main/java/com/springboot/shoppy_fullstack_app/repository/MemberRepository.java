package com.springboot.shoppy_fullstack_app.repository;

import com.springboot.shoppy_fullstack_app.dto.Member;

import java.util.Optional;

public interface MemberRepository {
    Optional<Member> findByMember(String id);
    String login(String id);
    int save(Member member);
    Long findById(String id);
}