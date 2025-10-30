package com.springboot.shoppy_fullstack_app.controller;

import com.springboot.shoppy_fullstack_app.dto.Member;
import com.springboot.shoppy_fullstack_app.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/idcheck")
    public String idcheck(@RequestBody Member member) {
        boolean result = memberService.idCheck(member.getId());  //아이디 O: 1, X: 0
        String msg = "";
        if(result) msg = "이미 사용중인 아이디 입니다.";
        else msg = "사용이 가능한 아이디 입니다.";
        return msg;
    }

    @PostMapping("/signup")
    public boolean signup(@RequestBody Member member) {
        boolean result = false;
        int rows = memberService.signup(member);
        if(rows == 1) result = true;
        return result;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Member member,
                                   HttpServletRequest request) {
        ResponseEntity<?> response = null;
        boolean result = memberService.login(member);
        if(result) {
            HttpSession session = request.getSession(true);
            session.setAttribute("sid", "hong");
            response = ResponseEntity.ok(Map.of("login", true));
        } else {
            response = ResponseEntity.ok(Map.of("login", false));
        }

        return response;
    }

}