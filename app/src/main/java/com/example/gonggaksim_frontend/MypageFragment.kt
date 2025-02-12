package com.example.gonggaksim_frontend

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import com.google.android.material.bottomsheet.BottomSheetDialog

class MypageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_mypage, container, false)

        val logoutBtn = view.findViewById<View>(R.id.tvLogoutBtn) // 로그아웃 버튼
        logoutBtn.setOnClickListener {
            showLogoutDialog()
        }

        return view
        //return inflater.inflate(R.layout.fragment_mypage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnModifyInformation = view.findViewById<ImageButton>(R.id.modifyBtn)
        btnModifyInformation.setOnClickListener{
            val intent = Intent(requireContext(), ModifyInformationActivity::class.java)
            Log.d("please", "wow!!!!")
            startActivity(intent)  //ModifyInformationActivity로 이동
        }

        val btnHelp = view.findViewById<ImageButton>(R.id.tvHelpBtn) // 도움말 버튼 가져오기
        btnHelp.setOnClickListener {
            val intent = Intent(requireContext(), HelpActivity::class.java)
            Log.d("hp", "wow!!!!")
            startActivity(intent) // HelpActivity로 이동
        }

        val btnwithdrawal = view.findViewById<ImageButton>(R.id.tvDeleteAccountBtn)
        btnwithdrawal.setOnClickListener{
            val intent = Intent(requireContext(),MembershipWithdrawalActivity::class.java)
            Log.d("dam", "wow!!!!")
            startActivity(intent) //MembershipWithdrawalActivity 이동
        }
    }

    @SuppressLint("MissingInflatedId")
    private fun showLogoutDialog() {
        val dialog = BottomSheetDialog(requireContext())
        val view = layoutInflater.inflate(R.layout.activity_dialog_logout, null)

        val btnConfirm = view.findViewById<Button>(R.id.btnConfirm) // 로그아웃 버튼
        val btnCancel = view.findViewById<ImageButton>(R.id.btnCancel)   // 취소 버튼

        btnConfirm.setOnClickListener {
            // 로그아웃 로직 실행 (예: SharedPreferences 삭제, 로그인 화면 이동)
            dialog.dismiss()
        }

        btnCancel.setOnClickListener {
            dialog.dismiss() // 팝업 닫기
        }

        dialog.setContentView(view)
        dialog.show()
    }


}