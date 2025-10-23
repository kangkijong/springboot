import React from 'react';
import { addCartItem, updateCartCount, 
         showCartItem, updateTotalPrice,
         updateCartItem, removeCartItem } from './cartSlice.js';
import { axiosData, axiosPost } from '../../utils/dataFetch.js';


/** 회원아이디별 장바구니 카운트 */
export const getCartCount = (id) => async(dispatch) => {
    const url = "/cart/count";
    const data = {"id": id};
    const jsonData = await axiosPost(url, data);
    dispatch(updateCartCount({"count": jsonData.sumQty}));
//    return jsonData.sumQty;
}

export const removeCart = (cid) => async(dispatch) => {
    const url = "/cart/deleteItem";
    const data = {"cid": cid};
    const rows = await axiosPost(url, data);
    console.log("delete rows => ", rows);
    const { userId } = JSON.parse(localStorage.getItem("loginInfo"));
    dispatch(getCartCount(userId));
    dispatch(showCart());

//    dispatch(removeCartItem({"cid": cid}));
//    dispatch(updateTotalPrice());
//    dispatch(updateCartCount());
}

export const showCart = () => async (dispatch) => {
//    const jsonData = await axiosData("/data/products.json");
    const url = "/cart/list";
    const { userId } = JSON.parse(localStorage.getItem("loginInfo"));
    const jsonData = await axiosPost(url, {"id": userId});
    console.log("jsonData showcart-->", jsonData);
    dispatch(showCartItem({"items": jsonData}));
    dispatch(updateTotalPrice({"totalPrice" : jsonData[0].totalPrice}));
}

export const updateCart = (cid, type) => async(dispatch) => {
    const url = "/cart/updateQty";
    const data = {"cid": cid, "type": type};
    const rows = await axiosPost(url, data);
    console.log('updateCart rows => ', rows, type);
    const { userId } = JSON.parse(localStorage.getItem("loginInfo"));
    dispatch(getCartCount(userId));
    dispatch(showCart());
    return rows;
//    dispatch(updateCartItem({"cid":cid, "type":type})); //수량변경
//    dispatch(updateTotalPrice());
//    dispatch(updateCartCount());
}

export const checkQty = async(pid, size, id) => {
    //쇼핑백 추가한 상품과 사이즈가 DB 테이블에 있는지 유무 확인
    const url = "/cart/checkQty";
    const data = {"pid": pid, "size": size, "id": id};
console.log(data);
    const jsonData = await axiosPost(url, data);
console.log("jsonData --> ", jsonData);
    return jsonData;
}

export const addCart = (pid, size) => async (dispatch) => {
    const { userId } = JSON.parse(localStorage.getItem("loginInfo"));
    const checkResult = await checkQty(pid, size, userId);
    if(!checkResult.checkQty) {
        const url = "/cart/add";
        const item = {"pid":pid, "size":size, "qty":1, "id": userId};
        const rows = await axiosPost(url, item);
        alert("상품이 추가되었습니다");
      } else {
        const rows = await updateCart(checkResult.cid, "+");
        alert("상품이 추가되었습니다");
      }
      dispatch(getCartCount(userId));

    return 1;
}