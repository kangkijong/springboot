package com.springboot.shoppy_fullstack_app.repository;

import com.springboot.shoppy_fullstack_app.dto.Product;
import com.springboot.shoppy_fullstack_app.dto.ProductDetailinfo;
import com.springboot.shoppy_fullstack_app.dto.ProductQna;

import java.util.List;

public interface ProductRepository {
    List<ProductQna> findQna(int pid);
    ProductDetailinfo findProductDetailinfo(int pid);
    Product findByPid(int pid);
    List<Product> findAll();
}