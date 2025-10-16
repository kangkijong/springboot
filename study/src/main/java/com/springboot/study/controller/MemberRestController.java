package com.springboot.study.controller;

import com.springboot.study.dto.Member;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController //내부에 @ResponseBody 포함, Map 객체 생성 없이 JSON 객체 바로 전송
@RequestMapping("/member")
public class MemberRestController {

    @PostMapping("/restSignup")
    public Member restSignup(@RequestBody Member member) {
        return member;
    }

    @PostMapping("/restLogin")
    public Map<String, Object> restLogin(@RequestBody Member member) {
        boolean result = false;
        if(member.getId().equals("test") && member.getPass().equals("1234")) result = true;

        //Map 객체를 생성하여 전송 -> 자동으로 JSON 객체로 변환
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("result", result);
        response.put("member", member);

        return response; //호출한 페이지로 문자열 혹은 JSON 객체로 전송 : {"result": true}
    }

}
