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
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findByPid(int pid); //상품 상세 조회
    List<Product> findAll();  //상품 전체 리스트 조회

    //상품 상세 - Return & Delivery
    @Query("select r from ProductReturn r")
    ProductReturn findReturn();

    //상품 상세 - Detailinfo
    @Query("""
               select distinct p
                    from Product p
                    left join fetch p.detailInfo di
                    where p.id = :pid
           """)
    Optional<Product> findProductWithDetail(@Param("pid") int pid);

    //상품 상세 - Qna
    @Query("""
            select distinct p
                from Product p
                left join fetch p.qnaList qna
                where p.pid = :pid
            """)
    Product findProductWithQna(@Param("pid") int pid);

}






