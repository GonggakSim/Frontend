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

    val api: ExamApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)  // 기본 URL 설정
            .addConverterFactory(GsonConverterFactory.create()) // JSON 변환기 설정
            .build()

        retrofit.create(ExamApi::class.java) // ExamApi 인터페이스 생성
    }

    val apiService: SearchApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SearchApiService::class.java)
    }


}
