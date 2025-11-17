package com.springboot.shoppy_fullstack_app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="view_cartlist")
@Setter @Getter
public class CartListView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cid;

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
    private int totalPrice;
}
