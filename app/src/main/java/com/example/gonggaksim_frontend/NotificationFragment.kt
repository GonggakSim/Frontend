package com.example.gonggaksim_frontend

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

        return binding.root
    }

    // 바텀시트 다이얼로그 표시
    private fun showDNDBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetView = layoutInflater.inflate(R.layout.dnb_bottomsheet, null)

        // 바텀시트 다이얼로그에 레이아웃 설정
        bottomSheetDialog.setContentView(bottomSheetView)

        // 닫기 버튼 이벤트 (dnb_bottomsheet.xml 내 닫기 버튼 ID가 btn_close인 경우)
        bottomSheetView.findViewById<View>(R.id.btn_close)?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        // 완료 버튼 이벤트 (dnb_bottomsheet.xml 내 완료 버튼 ID가 btn_done인 경우)
        bottomSheetView.findViewById<View>(R.id.btn_done)?.setOnClickListener {
            // 완료 버튼 클릭 시 처리할 로직 추가
            // 예: 선택한 요일 및 시간을 저장하거나 UI 갱신
            bottomSheetDialog.dismiss()
        }

        // 바텀시트 다이얼로그 표시
        bottomSheetDialog.show()
    }
}