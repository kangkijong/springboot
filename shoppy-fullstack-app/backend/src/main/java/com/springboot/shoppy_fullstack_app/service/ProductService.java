package com.springboot.shoppy_fullstack_app.service;

import com.springboot.shoppy_fullstack_app.dto.Product;
import java.util.List;

public interface ProductService {
    Product findByPid(int pid);
    List<Product> findAll();
}