package com.springboot.shoppy_fullstack_app.dto;

import lombok.*;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponseDto<T> {
    private List<SupportDto> list;
    private long totalCount;
    private int totalPage;
    private int currentPage;
}
