import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.gonggaksim_frontend.ApiService
import com.example.gonggaksim_frontend.OnboardingActivity
import com.example.gonggaksim_frontend.R
import com.example.gonggaksim_frontend.RetrofitClient
import com.example.gonggaksim_frontend.accountDeleteResponse
import com.example.gonggaksim_frontend.logoutResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MembershipPopUpActivity(context: Context) : Dialog(context) {
    private val profileService = RetrofitClient.retrofit.create(ApiService::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_membership_pop_up)

        val nextButton0 = findViewById<Button>(R.id.buttonO) // 탈퇴
        nextButton0.setOnClickListener {

            dismiss() // 팝업 닫기
            deleteAccount()
        }

        val nextButton1 = findViewById<Button>(R.id.button1) // 취소
        nextButton1.setOnClickListener {
            dismiss() // 팝업 닫기
        }
    }
    private fun deleteAccount(){
        val token : String? = getToken()
        val authToken = "Bearer ${token}"
        profileService.deleteAccount(authToken).enqueue(object : Callback<accountDeleteResponse> {
            override fun onResponse(call: Call<accountDeleteResponse>, response: Response<accountDeleteResponse>) {
                if (response.isSuccessful) {
                    // ✅ 계정 탈퇴 성공 시 onboarding_new Activity로 이동
                    val intent = Intent(context, OnboardingActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK  // 백스택 제거
                    context.startActivity(intent)
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("AccountDeleteError", "에러 응답: $errorBody")

                    val errorMessage = errorBody ?: "알 수 없는 오류가 발생했습니다."
                    Toast.makeText(context, "계정 탈퇴에 실패했습니다: $errorMessage", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<accountDeleteResponse>, t: Throwable) {
                // ❌ 네트워크 오류 또는 API 호출 실패
                Toast.makeText(context, "네트워크 오류로 계정 탈퇴에 실패했습니다.", Toast.LENGTH_SHORT).show()
                Log.e("AccountDeleteError", "API 호출 실패: ${t.localizedMessage}")
            }
        })

    }
    private fun getToken(): String? {
        val sharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
        return sharedPreferences.getString("accessToken", null)
    }
}
