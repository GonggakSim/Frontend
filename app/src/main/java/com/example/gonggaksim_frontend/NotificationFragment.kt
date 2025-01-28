package com.example.gonggaksim_frontend

import OxDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gonggaksim_frontend.databinding.FragmentNotificationBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class NotificationFragment : Fragment() {
    private lateinit var binding: FragmentNotificationBinding
    private lateinit var dndTimeAdapter: DNDTimeAdapter
    private val dndTimeList = mutableListOf<DNDTime>() // 데이터 리스트

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // 바인딩 초기화
        binding = FragmentNotificationBinding.inflate(inflater, container, false)

        setupRecyclerView()

        // addDND 텍스트 클릭 이벤트 설정
        binding.addDND.setOnClickListener {
            val dndBottomSheet = DNDBottomSheetFragment()
            dndBottomSheet.show(childFragmentManager, "DNDBottomSheet")
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
    private fun setupRecyclerView() {
        dndTimeAdapter = DNDTimeAdapter(dndTimeList)
        binding.recyclerViewDNDTime.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dndTimeAdapter
        }
    }

    fun addDNDTime(dndTime: DNDTime) {
        Log.d("DND_DEBUG", "addDNDTime 호출됨: ${dndTime.days}, ${dndTime.startTime} - ${dndTime.endTime}")

        dndTimeList.add(dndTime)
        dndTimeAdapter.notifyItemInserted(dndTimeList.size - 1)
        Log.d("DND_DEBUG", "RecyclerView 업데이트됨: ${dndTimeList.size} 개의 아이템")

    }
}