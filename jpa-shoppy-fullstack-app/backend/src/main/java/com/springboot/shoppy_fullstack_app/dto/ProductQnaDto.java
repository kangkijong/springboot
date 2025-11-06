package com.springboot.shoppy_fullstack_app.dto;

import com.springboot.shoppy_fullstack_app.entity.ProductQna;
import lombok.Data;

@Data
public class ProductQnaDto {
    private int qid;
    private String title;
    private String content;
    private boolean isComplete;
    private boolean isLock;
    private String id;
    private int pid;
    private String cdate;

    //Entity <-> Dto 변환
    public ProductQnaDto() {}   //기본 생성자
    public ProductQnaDto(ProductQna entity) {
        this.qid = entity.getQid();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.isComplete = entity.getIsComplete();
        this.isLock = entity.getIsLock();
        this.id = entity.getId();
        this.pid = entity.getPid();
        this.cdate = entity.getCdate();
    }
}
