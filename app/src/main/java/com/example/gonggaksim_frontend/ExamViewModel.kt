package com.example.gonggaksim_frontend

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// 예시: ViewModel에서 LiveData를 사용하여 UI 업데이트
class ExamViewModel : ViewModel() {
    private val repository = ExamRepository()

    val examResponse = MutableLiveData<ExamResponse?>()
    val error = MutableLiveData<String>()

    fun registerExam(certificationId: String, provider: String) {
        viewModelScope.launch {
            repository.registerExam(certificationId, provider,
                callback = { response ->
                    // 성공적으로 응답을 받으면 LiveData에 값 할당
                    examResponse.value = response
                },
                errorCallback = { error ->
                    // 에러 발생 시 에러 메시지 LiveData에 값 할당
                    this@ExamViewModel.error.value = error.message
                })
        }
    }
}

