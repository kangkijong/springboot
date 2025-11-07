package com.springboot.shoppy_fullstack_app.entity;

import com.springboot.shoppy_fullstack_app.dto.CartItemDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name="cart")
@Getter @Setter
public class CartItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cid;

    private String size;
    private int qty;
    private int pid;
    private String id;
    private LocalDate cdate;    //String -> 자바에서 지원하는 타입인 LocalDate로 변경

    //Dto <-> Entity 변환
    public CartItem() {}    //생성자
    public CartItem(CartItemDto dto) {
        this.cid = dto.getCid();
        this.size = dto.getSize();
        this.qty = dto.getQty();
        this.pid = dto.getPid();
        this.id = dto.getId();
        this.cdate = LocalDate.now();
    }
}

//cid	int	NO
//size	char(2)	NO
//qty	int	NO
//pid	int	NO
//id	varchar(50)	NO
//cdate	datetime	NO