package com.springboot.shoppy_fullstack_app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name="product_qna")
@Getter @Setter
public class ProductQna {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int qid;   // PK

    private String title;
    private String content;
    private Boolean isComplete;
    private Boolean isLock;
    private String id;
    private int pid;
    private String cdate;
}

//qid	int	NO
//title	varchar(100)	NO
//content	varchar(200)	YES
//is_complete	tinyint(1)	YES
//is_lock	tinyint(1)	YES
//id	varchar(50)	NO
//pid	int	NO
//cdate	datetime	YES