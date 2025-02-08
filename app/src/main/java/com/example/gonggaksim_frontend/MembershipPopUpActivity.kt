import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import com.example.gonggaksim_frontend.MainActivity
import com.example.gonggaksim_frontend.MembershipWithdrawalActivity
import com.example.gonggaksim_frontend.R

class MembershipPopUpActivity(context: Context) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_membership_pop_up)

        val nextButton0 = findViewById<Button>(R.id.buttonO)
        nextButton0.setOnClickListener {
            // MembershipWithdrawalActivity 이동
            context.startActivity(Intent(context, MembershipWithdrawalActivity::class.java))
            dismiss() // 팝업 닫기
        }

        val nextButton1 = findViewById<Button>(R.id.button1)
        nextButton1.setOnClickListener {
            // MembershipWithdrawalActivity 이동
            context.startActivity(Intent(context, MembershipWithdrawalActivity::class.java))
            dismiss() // 팝업 닫기
        }
    }
}
