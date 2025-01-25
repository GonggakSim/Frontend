package com.example.gonggaksim_frontend

import OxDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.gonggaksim_frontend.databinding.FragmentNotificationBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // 바인딩 초기화
        binding = FragmentNotificationBinding.inflate(inflater, container, false)

        // addDND 텍스트 클릭 이벤트 설정
        binding.addDND.setOnClickListener {
            showDNDBottomSheet()
        }

        binding.oxbtn.setOnClickListener{
            showOXDialog(
                context = requireContext(),
                title = "틀렸습니다!",
                content = "SMTP는 사용자가 작성한 이메일을 다른 사람의 계정으로 전송해주는 역할을 합니다."
            )
        }
        binding.multibtn.setOnClickListener{
            showMultiDialog(
                context = requireContext(),
                title = "틀렸습니다!",
                content = "SMTP는 사용자가 작성한 이메일을 다른 사람의 계정으로 전송해주는 역할을 합니다."
            )
        }
        binding.dictatebtn.setOnClickListener{
            showDictateDialog(
                context = requireContext(),
                title = "틀렸습니다!",
                content = "SMTP는 사용자가 작성한 이메일을 다른 사람의 계정으로 전송해주는 역할을 합니다."
            )
        }

        return binding.root
    }

    private fun showDNDBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetView = layoutInflater.inflate(R.layout.dnb_bottomsheet, null)

        bottomSheetDialog.setContentView(bottomSheetView)

        bottomSheetView.findViewById<View>(R.id.btn_close)?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetView.findViewById<View>(R.id.btn_done)?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
    }
    fun showMultiDialog(context: Context, title: String, content: String) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.test_noti_multi)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent) // 배경 투명

        dialog.show()
    }
    fun showDictateDialog(context: Context, title: String, content: String) {
        val dialog = Dialog(context)
        dialog.setContentView(R.layout.test_noti_dictate)
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent) // 배경 투명

        dialog.show()
    }
    fun showOXDialog(context: Context, title: String, content: String) {
        val dialog = OxDialog(context)
        dialog.show()
    }
}