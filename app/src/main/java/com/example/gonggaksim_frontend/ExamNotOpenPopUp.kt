package com.example.gonggaksim_frontend

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.gonggaksim_frontend.databinding.FragmentExamNotOpenPopupBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExamNotOpenPopUp(
    context: Context,
    private var authToken: String,
    private var message: String,
    private var certificationId: String,
    private val scheduleId: Int,  // scheduleId 추가
    private val userId: Int,      // userId 추가
    private val onDeleteConfirmed: (Boolean) -> Unit,
) : Dialog(context) {

    private var scheduleService = RetrofitClient.retrofit.create(ApiService::class.java)
    private lateinit var binding: FragmentExamNotOpenPopupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentExamNotOpenPopupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window?.setBackgroundDrawableResource(android.R.color.transparent)

        message = message.substring(27, 51).trim()
        binding.registrationPeriod.text = message

        binding.btnNoCheck.setOnClickListener { dismiss() }

        binding.btnCheck.setOnClickListener {
            val call = scheduleService.getCertificationNotifications(
                authToken = authToken,
                certificationId = certificationId,
                request = UserRequestNotifications(scheduleId, userId),
                provider = ""
            )
            Log.d("ExamNot", "$call")

            call.enqueue(object : Callback<UserResponseNotifications> {
                override fun onResponse(
                    call: Call<UserResponseNotifications>,
                    response: Response<UserResponseNotifications>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            if (it.success) {
                                Toast.makeText(binding.root.context, "시험 접수 성공!", Toast.LENGTH_SHORT).show()
                                Log.d("ExamNot", "시험 접수 성공!!")
                                dismiss()
                            } else {
                                Toast.makeText(binding.root.context, "userId와 scheduleId 확인 필요", Toast.LENGTH_SHORT).show()
                                Log.d("ExamNot", "userId와 scheduleId 확인 필요")
                            }
                        }
                    } else {
                        Log.e("ExamNot", "통신 오류 발생")
                        Toast.makeText(binding.root.context, "통신 오류 발생", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<UserResponseNotifications>, t: Throwable) {
                    Log.e("ExamNot", "API 호출 실패: ${t.message}")
                    Toast.makeText(binding.root.context, "네트워크 오류 발생", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
