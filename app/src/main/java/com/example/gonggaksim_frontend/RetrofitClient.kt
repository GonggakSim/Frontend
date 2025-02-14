package com.example.gonggaksim_frontend

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    // base url
    private const val BASE_URL = "http://13.125.231.189:3000/"

    // 네트워크 요청과 응답에 대한 로그 출력
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        // 요청과 응답의 전체 내용(Body 포함) 출력 - API 요청/응답 확인용
        level = HttpLoggingInterceptor.Level.BODY
    }

    // 네트워크 요청을 처리할 클라이언트
    private val client = OkHttpClient.Builder()
        // 로그 인터셉트 추가(위 요청/응답 로그에 대한 것)
        .addInterceptor(loggingInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)  // 연결 시간 초과 설정
        .readTimeout(30, TimeUnit.SECONDS)     // 읽기 시간 초과 설정
        .writeTimeout(30, TimeUnit.SECONDS)    // 쓰기 시간 초과 설정
        // 해당 객체 생성
        .build()

    // Retrofit 인스턴스 생성/반환
    fun getRetrofit(): Retrofit {
        val retrofit = Retrofit.Builder()
            // API 요청 시 기본 url 경로를 사용
            .baseUrl(BASE_URL)
            // 위 클라이언트 Retrofit에 적용
            .client(client)
            // JSON 응답을 자동으로 코틀린으로 변환
            .addConverterFactory(GsonConverterFactory.create())
            // 객체 생성
            .build()

        // 생성된 retrofit을 반환하도록
        return retrofit
    }
}