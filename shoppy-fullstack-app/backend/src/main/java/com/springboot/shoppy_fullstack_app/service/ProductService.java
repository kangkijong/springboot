package com.springboot.shoppy_fullstack_app.service;

import com.springboot.shoppy_fullstack_app.dto.Product;
import com.springboot.shoppy_fullstack_app.dto.ProductDetailinfo;
import com.springboot.shoppy_fullstack_app.dto.ProductQna;

import java.util.List;

public interface ProductService {
    List<ProductQna> findQna(int pid);
    ProductDetailinfo findDetailinfo(int pid);
    Product findByPid(int pid);
    List<Product> findAll();
}