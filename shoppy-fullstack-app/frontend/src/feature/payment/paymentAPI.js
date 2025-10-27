import { axiosPost } from '../../utils/dataFetch.js';

export const getPayment = async(receiver) => {
    //userId, orderId, itemName, totalPrice ...
    const { userId } = JSON.parse(localStorage.getItem("loginInfo"));
    const url = "/payment/kakao/ready";  //카카오 QR 코드 호출
    const data = {
        "orderId": "",
        "userId": userId,
        "itemName": "test",
        "qty": "10",
        "totalAmount": "1000",
        "receiver": receiver
    }

    try {
        const kakaoReadyResult = await axiosPost(url, data);
        console.log("kakaoReadyResult => ", kakaoReadyResult);
        if(kakaoReadyResult.tid) {
            //새로운 페이지 연결
            window.location.href = kakaoReadyResult.next_redirect_pc_url;
        }

    } catch(error) {
        console.log("error :: ", error);
    }
}