package com.springboot.shoppy_fullstack_app.dto;

import lombok.Data;

@Data
public class ProductQna {
    private int qid;
    private String title;
    private String content;
    private boolean isComplete;
    private boolean isLock;
    private String id;
    private int pid;
    private String cdate;
}

//qid	int
//title	varchar(100)
//content	varchar(200)
//is_complete	tinyint(1)
//is_lock	tinyint(1)
//id	varchar(50)
//pid	int
//cdate	datetime
