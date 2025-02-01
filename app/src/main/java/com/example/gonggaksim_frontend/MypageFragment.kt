package com.example.gonggaksim_frontend

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton

class MypageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mypage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnHelp = view.findViewById<ImageButton>(R.id.tvHelpBtn) // 도움말 버튼 가져오기

        btnHelp.setOnClickListener {
            val intent = Intent(requireContext(), HelpActivity::class.java)
            startActivity(intent) // HelpActivity로 이동
        }

        val btnwithdrawal = view.findViewById<ImageButton>(R.id.tvDeleteAccountBtn)

        btnwithdrawal.setOnClickListener{
            val intent = Intent(requireContext(),MembershipWithdrawalActivity::class.java)
            startActivity(intent) //MembershipWithdrawalActivity 이동
        }



    }


}