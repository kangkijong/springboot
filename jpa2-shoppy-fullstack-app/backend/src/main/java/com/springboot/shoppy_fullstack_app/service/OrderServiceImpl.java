package com.springboot.shoppy_fullstack_app.service;

import com.springboot.shoppy_fullstack_app.dto.KakaoPayDto;
import com.springboot.shoppy_fullstack_app.entity.Member;
import com.springboot.shoppy_fullstack_app.entity.Order;
import com.springboot.shoppy_fullstack_app.entity.OrderStatus;
import com.springboot.shoppy_fullstack_app.repository.CartRepository;
import com.springboot.shoppy_fullstack_app.repository.OrderRepository;
import com.springboot.shoppy_fullstack_app.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private CartRepository cartRepository;
    private MemberRepository memberRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            CartRepository cartRepository,
                            MemberRepository memberRepository){
        this.orderRepository = orderRepository;
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public int save(KakaoPayDto kakaoPayDto) {
        int result = 0;
        //Step1 : Orders 테이블 저장
        Optional<Member> member = memberRepository.findById(kakaoPayDto.getUserId());
        Order entity = orderRepository.save(new Order(kakaoPayDto, member.get()));
        if(entity == null) new Exception("step1 주문테이블 저장 실패!!");

        //Step2 : Order_detail 테이블 저장
        int rows = orderRepository.saveOrderDetail(kakaoPayDto.getOrderId(),
                                kakaoPayDto.getPaymentInfo().getDiscountAmount(),
                                kakaoPayDto.getCidList());
        if(rows == 0) new Exception("step2 주문 상세 테이블 저장 실패!!");

        //Step3 : Cart 테이블 아이템 삭제 - JpaCartRepository에서 삭제 진행
        int cartRows = cartRepository.deleteItemList(kakaoPayDto.getCidList());
        if(cartRows == 0) new Exception("step3 장바구니 아이템 삭제 실패!!");

        result = 1;

        return result;
    }
}
