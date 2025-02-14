package com.example.gonggaksim_frontend

import com.google.gson.annotations.SerializedName
import retrofit2.http.Header
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

// Retrofit으로 API를 호출할 서비스 인터페이스
interface AuthService {
    // 요청 보낼 서버의 엔드포인트 지정 (Google 로그인 ID 토큰을 서버로 전송하는 역할)
    @POST("/oauth2/login/google/")
    // 요청 Body에 토큰을 포함한 데이터 전송
    // TokenRequest를 사용하여 JSON 형식으로 변환
    // 서버에서의 응답을 LoginResponse 객체로 반환 (비동기 요청)
    fun loginWithGoogle(@Body token: TokenRequest): Call<LoginResponse>
}

// ID 토큰을 서버로 전송하기 위한 데이터 클래스
data class TokenRequest(
    // idToken 이라는 키로 데이터를 직렬화 (idToken 키가 idToken 변수에 매핑)
    // Google 로그인에서 받은 ID 토큰 저장
    @SerializedName("idToken") val idToken: String
)
