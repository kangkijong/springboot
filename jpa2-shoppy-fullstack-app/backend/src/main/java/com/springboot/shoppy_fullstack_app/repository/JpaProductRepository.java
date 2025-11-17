package com.springboot.shoppy_fullstack_app.repository;

import com.springboot.shoppy_fullstack_app.entity.Product;
import com.springboot.shoppy_fullstack_app.entity.ProductDetailinfo;
import com.springboot.shoppy_fullstack_app.entity.ProductQna;
import com.springboot.shoppy_fullstack_app.entity.ProductReturn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface JpaProductRepository  extends JpaRepository<Product, Integer> {
    //상품 상세 - Return & Delivery
    @Query("select r from ProductReturn r")
    ProductReturn findReturn();

    //상품 상세 - QnA
    @Query("select q from ProductQna q where q.pid = :pid")
    List<ProductQna> findQna(@Param("pid") int pid);

    //상품 상세 - 디데일 탭
    @Query("select d from ProductDetailinfo d where d.pid = :pid")
    ProductDetailinfo findProductDetailinfo(@Param("pid") int pid);
    
    Product findByPid(int pid); //상품 상세 조회
//    List<Product> findAll();  //상품 전체 리스트 조회
}





