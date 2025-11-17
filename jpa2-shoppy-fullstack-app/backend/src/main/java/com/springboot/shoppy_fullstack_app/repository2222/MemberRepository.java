package com.springboot.shoppy_fullstack_app.repository2222;

import com.springboot.shoppy_fullstack_app.dto.MemberDto;

import java.util.Optional;

public interface MemberRepository {
    Optional<MemberDto> findByMember(String id);
    String login(String id);
    int save(MemberDto member);
    Long findById(String id);
}
