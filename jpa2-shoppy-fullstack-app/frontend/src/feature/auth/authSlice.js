import { createSlice } from '@reduxjs/toolkit'

const saveAuth = JSON.parse(localStorage.getItem("auth"));

const initialState = saveAuth || {
  isLogin: false,
  role: ''
}

export const authSlice = createSlice({
  name: 'auth',
  initialState,
  reducers: {
        login(state, action) {
            const { userId, role } = action.payload;
            state.isLogin = !state.isLogin;
            state.role = role;

            const loginInfo = {"userId": userId};
            localStorage.setItem("loginInfo", JSON.stringify(loginInfo));

            //새로고침을 위한 데이터 복사(localStorage 저장)
            localStorage.setItem("auth",
                                JSON.stringify({
                                    isLogin : true,
                                    userId,
                                    role
                                })
            )
        },
        logout(state, action) {
            state.isLogin = !state.isLogin;
            state.role = '';

            //로컬스토리지 정보 삭제
            localStorage.removeItem("loginInfo");
            localStorage.removeItem("auth");
            localStorage.removeItem("cart");
        },
        reLogin (state, action){
            const { userId, isLogin, role } = action.payload;
            console.log(userId, isLogin, role);
//            localStorage.removeItem("loginInfo");
//            localStorage.removeItem("auth");
//            localStorage.setItem("loginInfo", JSON.stringify(loginInfo));
//            //새로고침을 위한 데이터 복사(localStorage 저장)
//            localStorage.setItem("auth",
//                                JSON.stringify({
//                                    isLogin : true,
//                                    userId,
//                                    role
//                                })
//            )
        }
  },
})

export const { login, logout, reLogin }
    = authSlice.actions   //API 함수 또는 컴포넌트에서 dispatch(액션함수)

export default authSlice.reducer  //store  import