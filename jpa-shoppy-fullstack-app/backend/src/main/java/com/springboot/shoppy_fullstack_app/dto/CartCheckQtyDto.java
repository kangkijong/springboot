package com.springboot.shoppy_fullstack_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter @Setter
@AllArgsConstructor
public class CartCheckQtyDto {
    private int cid;
    private Long count;

    //클래스의 필드에 데이터를 주입(Injection)하는 2가지 방법
    //1. 생성자
    //2. Setter 메소드
//    public CartCheckQtyDto() {} //생성자
//    public CartCheckQtyDto(int cid, Long count) {
//        this.cid = cid;
//        this.count = count;
//    }
}
