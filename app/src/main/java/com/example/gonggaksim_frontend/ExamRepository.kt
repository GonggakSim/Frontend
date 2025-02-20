package com.example.gonggaksim_frontend

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExamRepository {

    // 인증 ID와 제공자 정보로 시험 등록 API 호출
    fun registerExam(certificationId: String, provider: String, callback: (ExamResponse?) -> Unit, errorCallback: (Throwable) -> Unit) {
        // RetrofitClient에서 제공하는 api를 통해 실제 API 호출
        RetrofitClient.api.registerExam(certificationId, provider).enqueue(object : Callback<ExamResponse> {
            override fun onResponse(call: Call<ExamResponse>, response: Response<ExamResponse>) {
                if (response.isSuccessful) {
                    callback(response.body()) // 성공 시 콜백 호출
                } else {
                    errorCallback(Throwable("API 호출 실패")) // 실패 시 에러 콜백 호출
                }
            }

            override fun onFailure(call: Call<ExamResponse>, t: Throwable) {
                errorCallback(t) // 네트워크 실패 시 에러 콜백 호출
            }
        })
    }
}
