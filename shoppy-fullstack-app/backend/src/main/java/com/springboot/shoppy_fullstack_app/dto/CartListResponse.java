package com.springboot.shoppy_fullstack_app.dto;

import lombok.Data;

@Data
public class CartListResponse {
    private String id;
    private int pid;
    private String image;
    private int price;
    private String size;
    private int qty;
    private int cid;
    private int totalPrice;
}