package com.example.gonggaksim_frontend

import android.app.Application
import android.util.Log
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility

class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 키 해시 출력
        val keyHash = Utility.getKeyHash(this)
        Log.i("GlobalApplication", "KeyHash: $keyHash")

        // Kakao Sdk 초기화
        KakaoSdk.init(this, "ad232e97ad49f31881f71f07e5e98a03")
    }
}