package com.example.gonggaksim_frontend

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val BASE_URL = "http://13.209.11.7:3000/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val instance: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    // 네트워크 요청을 처리할 OkHttpClient
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)       // 로깅 인터셉터 추가
        .connectTimeout(30, TimeUnit.SECONDS)     // 연결 시간 초과
        .readTimeout(30, TimeUnit.SECONDS)        // 읽기 시간 초과
        .writeTimeout(30, TimeUnit.SECONDS)       // 쓰기 시간 초과
        .build()

    // Retrofit 인스턴스를 생성하는 메서드
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)// OkHttpClient 적용
            .addConverterFactory(GsonConverterFactory.create()) // JSON 변환기 추가
            .build()
    }

    // ApiService 인스턴스
    val instance: ApiService by lazy {
        getRetrofit().create(ApiService::class.java)
    }
}
