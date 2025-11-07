package com.springboot.shoppy_fullstack_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter @Getter
@AllArgsConstructor
public class CartListResponseDto {
    private String id;
    private String mname;
    private String phone;
    private String email;
    private int pid;
    private String name;
    private String info;
    private String image;
    private int price;
    private String size;
    private int qty;
    private int cid;
    private int totalPrice;
}
