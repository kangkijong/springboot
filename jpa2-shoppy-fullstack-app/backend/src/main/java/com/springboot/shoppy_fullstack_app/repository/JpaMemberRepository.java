package com.springboot.shoppy_fullstack_app.repository;

import com.springboot.shoppy_fullstack_app.dto.MemberDto;
import com.springboot.shoppy_fullstack_app.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//interface를 구현하는 클래스를 생성하는 작업은 Spring Data JPA
// --> 서버 부팅시 컨테이너에 생성 후 로딩
@Repository
public interface JpaMemberRepository extends JpaRepository<Member, String> {
    //Member save(Member member);  //✨✨ 생략 ⭕ :상속한 부모인터페이스에 save 메소드 존재

    //새로운 메소드 정의 - 1. 네이밍 규칙적용, 2. @Query 적용: SQL 직접 생성
    //@Query("select count(m) from Member m where id = :id")
    Long countById(String id);  //네이밍 규칙 적용 : 간단한 SQL 생성 후 실행결과 출력

    //로그인 - 엔티티가 아닌 다른 객체로 결과를 출력하는 경우 'new 패키지 풀주소 출력객체명'
    //        형식으로 컬럼리스트를 작성, 출력값에 따른 생성자 정의 필수!!!
    @Query("""
            select 
                new com.springboot.shoppy_fullstack_app.dto.MemberDto(m.id, m.pwd, m.role) 
            from Member m 
            where m.id = :id
            """)
    Optional<MemberDto> findByMember(@Param("id") String id);
}










