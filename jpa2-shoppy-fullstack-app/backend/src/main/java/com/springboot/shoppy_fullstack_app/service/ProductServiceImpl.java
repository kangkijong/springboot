package com.springboot.shoppy_fullstack_app.service;

import com.springboot.shoppy_fullstack_app.dto.ProductDto;
import com.springboot.shoppy_fullstack_app.dto.ProductDetailinfoDto;
import com.springboot.shoppy_fullstack_app.dto.ProductQnaDto;
import com.springboot.shoppy_fullstack_app.dto.ProductReturnDto;
import com.springboot.shoppy_fullstack_app.entity.Product;
import com.springboot.shoppy_fullstack_app.entity.ProductDetailinfo;
import com.springboot.shoppy_fullstack_app.entity.ProductQna;
import com.springboot.shoppy_fullstack_app.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
//@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductReturnDto findReturn() {
        return new ProductReturnDto(productRepository.findReturn()); }

    @Override
    public List<ProductQnaDto> findQna(int pid) {
        List<ProductQnaDto> list = new ArrayList<>();
        Product product = productRepository.findProductWithQna(pid);
        List<ProductQna> qnaList = product.getQnaList();
        qnaList.forEach(qna -> list.add(new ProductQnaDto(qna)));
        return list;
    }

    @Override
    public ProductDetailinfoDto findDetailinfo(int pid) {
        Optional<Product> entity = productRepository.findProductWithDetail(pid);
        ProductDetailinfo detailinfo = entity.get().getDetailInfo();
        return new ProductDetailinfoDto(detailinfo);
    }

    @Override
    public ProductDto findByPid(int pid) {
        Product entity = productRepository.findByPid(pid);
        return new ProductDto(entity);
    }

    @Override
    public List<ProductDto> findAll() {
        List<ProductDto> dlist = new ArrayList<>();
        List<Product> list = productRepository.findAll();
        list.forEach((product) -> dlist.add(new ProductDto(product)));
        return dlist;
    }
}
