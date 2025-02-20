package com.example.gonggaksim_frontend

import MultiDialog
import OxDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gonggaksim_frontend.databinding.FragmentNotificationBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.chip.Chip
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationFragment : Fragment() {
    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    private lateinit var dndTimeAdapter: DNDTimeAdapter
    private val dndTimeList = mutableListOf<DNDTime>() // 데이터 리스트
    val quizService = RetrofitClient.retrofit.create(ApiService::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)

        setupRecyclerView()

        // DND 추가 버튼 클릭 이벤트
        binding.addDND.setOnClickListener {
            val dndBottomSheet = DNDBottomSheetFragment()
            dndBottomSheet.show(childFragmentManager, "DNDBottomSheet")
        }

        // 퀴즈 설정 버튼 클릭 이벤트
        binding.makeQuizBtn.setOnClickListener {
            val quizSettings = collectQuizSettings()
            Log.d("quizset", quizSettings.toString())
            fetchQuizData(quizSettings)
        }

        binding.makeQuizBtn2.setOnClickListener{
            showOnlyNotiDialog(requireContext())
        }

        return binding.root
    }
    private fun getToken(): String? {
        val sharedPreferences = requireActivity().getSharedPreferences("auth", Context.MODE_PRIVATE)
        return sharedPreferences.getString("accessToken", null)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // 메모리 누수 방지
    }

    // Multi Dialog 표시
    fun showMultiDialog(context: Context, quizData: QuizData) {
        val dialog = MultiDialog(context)
        dialog.setQuizData(quizData)
        dialog.show()
    }

    // Dictate Dialog 표시
    fun showDictateDialog(context: Context, quizData: QuizData) {
        val dialog = DictateDialog(context)
        dialog.setQuizData(quizData)
        dialog.show()
    }

    // OX Dialog 표시
    fun showOXDialog(context: Context, quizData: QuizData) {
        val dialog = OxDialog(context)
        dialog.setQuizData(quizData)
        dialog.show()
    }

    fun showOnlyNotiDialog(context: Context)
    {
        val dialog = NotiOnlyDialog(context)
        dialog.show()
    }

    // RecyclerView 초기화
    private fun setupRecyclerView() {
        dndTimeAdapter = DNDTimeAdapter(dndTimeList)
        binding.recyclerViewDNDTime.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = dndTimeAdapter
        }
    }

    // DND 시간 추가
    fun addDNDTime(dndTime: DNDTime) {
        Log.d("DND_DEBUG", "addDNDTime 호출됨: ${dndTime.days}, ${dndTime.startTime} - ${dndTime.endTime}")

        dndTimeList.add(dndTime)
        dndTimeAdapter.notifyItemInserted(dndTimeList.size - 1)
        Log.d("DND_DEBUG", "RecyclerView 업데이트됨: ${dndTimeList.size} 개의 아이템")
    }

    // 퀴즈 설정 수집 함수
    private fun collectQuizSettings(): QuizSettings {
        val chipGroupCertifications = binding.chipGroupQuiz
        val chipGroupSubjects = binding.chipGroupSubject

        // 선택된 Certifications (ChipGroup)
        val selectedCertifications = mutableListOf<String>()
        for (i in 0 until chipGroupCertifications.childCount) {
            val chip = chipGroupCertifications.getChildAt(i) as Chip
            if (chip.isChecked) {
                selectedCertifications.add(chip.text.toString())
            }
        }

        // 선택된 QuizTypes (CheckBoxes) - ViewBinding 사용
        val selectedQuizTypes = mutableListOf<String>()
        val checkBoxes = listOf(
            binding.checkOX,
            binding.checkMulti,
            binding.checkDictate,
            binding.checkAlertOnly
        )

        for (checkBox in checkBoxes) {
            if (checkBox.isChecked) {
                selectedQuizTypes.add(checkBox.text.toString())
            }
        }

        // 선택된 Subjects (ChipGroup)
        val selectedSubjects = mutableListOf<String>()
        for (i in 0 until chipGroupSubjects.childCount) {
            val chip = chipGroupSubjects.getChildAt(i) as Chip
            if (chip.isChecked) {
                selectedSubjects.add(chip.text.toString())
            }
        }

        // QuizSettings 객체 생성
        return QuizSettings(
            certifications = selectedCertifications,
            quizTypes = selectedQuizTypes,
            subjects = selectedSubjects,
            userId = 2 // 예시 User ID
        )
    }

    private fun fetchQuizData(quizSettings: QuizSettings) {
        val token : String? = getToken()
        val authToken = "Bearer ${token}" // 실제 토큰으로 대체
        val call = quizService.getQuizData(authToken, quizSettings)

        call.enqueue(object : Callback<UserResponseQuiz> {
            override fun onResponse(call: Call<UserResponseQuiz>, response: Response<UserResponseQuiz>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    Log.d("QuizAPI", "📋 Raw Response: $body")

                    val quizData = body?.dataWrapper?.quizData
                    if (quizData != null) {
                        handleQuizResponse(requireContext(), quizData)
                    } else {
                        Log.e("QuizAPI", "🚨 QuizData가 null입니다.")
                    }
                } else {
                    Log.e("QuizAPI", "🚨 실패: ${response.code()} - ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<UserResponseQuiz>, t: Throwable) {
                Log.e("QuizAPI", "❌ 네트워크 오류: ${t.message}")
            }
        })

    }

    private fun handleQuizResponse(context: Context, quizData: QuizData) {
        Log.d("QuizHandler", "📋 수신된 QuizData: $quizData")
        val quizType = quizData.quizType ?: ""

        when (quizType) {
            "기출문제" -> showMultiDialog(context, quizData)
            "OX" -> showOXDialog(context, quizData)
            "받아적기" -> showDictateDialog(context, quizData)
            else -> {
                Log.e("QuizHandler", "⚠️ 알 수 없는 퀴즈 타입 또는 null: '${quizData.quizType}'")
                Toast.makeText(context, "지원하지 않는 퀴즈 유형입니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}