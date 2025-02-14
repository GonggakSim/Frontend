import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.example.gonggaksim_frontend.MainActivity
import com.example.gonggaksim_frontend.R

class OnboardingPopUp(context: Context) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_pop_up)

        val nextButton = findViewById<Button>(R.id.buttonnext2)
        nextButton.setOnClickListener {
            // MainActivity로 이동
            context.startActivity(Intent(context, MainActivity::class.java))
            dismiss() // 팝업 닫기
        }
    }
}
