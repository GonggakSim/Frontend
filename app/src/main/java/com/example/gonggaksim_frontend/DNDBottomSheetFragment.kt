package com.example.gonggaksim_frontend

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import android.widget.Toast
import com.example.gonggaksim_frontend.databinding.DndBottomsheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip

class DNDBottomSheetFragment : BottomSheetDialogFragment() {

    private lateinit var binding: DndBottomsheetBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DndBottomsheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTimePickers()
        setupChipGroup()
        setupButtons()
    }

    private fun setupTimePickers() {
        val startAmpmPicker = binding.numberPickerAmPmStart
        val startHourPicker = binding.numberPickerHourStart
        val startMinutePicker = binding.numberPickerMinuteStart

        val endAmpmPicker = binding.numberPickerAmPmEnd
        val endHourPicker = binding.numberPickerHourEnd
        val endMinutePicker = binding.numberPickerMinuteEnd

        startAmpmPicker.minValue = 0
        startAmpmPicker.maxValue = 1
        startAmpmPicker.displayedValues = arrayOf("오전", "오후")

        endAmpmPicker.minValue = 0
        endAmpmPicker.maxValue = 1
        endAmpmPicker.displayedValues = arrayOf("오전", "오후")

        startHourPicker.minValue = 1
        startHourPicker.maxValue = 12
        endHourPicker.minValue = 1
        endHourPicker.maxValue = 12

        startMinutePicker.minValue = 0
        startMinutePicker.maxValue = 59
        endMinutePicker.minValue = 0
        endMinutePicker.maxValue = 59
    }

    private fun setupChipGroup() {
        binding.chipGroup.setOnCheckedChangeListener { _, _ ->
            val selectedDays = getSelectedDays()
            Log.d("DNDBottomSheet", "선택된 요일: $selectedDays")
        }
    }

    private fun getSelectedDays(): List<String> {
        val selectedDays = mutableListOf<String>()

        for (i in 0 until binding.chipGroup.childCount) {
            val chip = binding.chipGroup.getChildAt(i) as Chip
            if (chip.isChecked) {
                selectedDays.add(chip.text.toString())
            }
        }
        return selectedDays
    }

    private fun setupButtons() {
        binding.btnDone.setOnClickListener {
            val selectedDays = getSelectedDays()

            if (selectedDays.isEmpty()) {
                Toast.makeText(requireContext(), "요일을 선택하세요!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val startTime = getTime(binding.numberPickerAmPmStart, binding.numberPickerHourStart, binding.numberPickerMinuteStart)
            val endTime = getTime(binding.numberPickerAmPmEnd, binding.numberPickerHourEnd, binding.numberPickerMinuteEnd)

            val dndTime = DNDTime(selectedDays, startTime, endTime)

            Log.d("DND_DEBUG", "바텀시트에서 전달: ${dndTime.days}, ${dndTime.startTime} - ${dndTime.endTime}")

            val parentFragment = parentFragment as? NotificationFragment
            parentFragment?.addDNDTime(dndTime) ?: Log.e("DND_DEBUG", "NotificationFragment를 찾을 수 없음!")

            dismiss()
        }
    }

    private fun getTime(ampmPicker: NumberPicker, hourPicker: NumberPicker, minutePicker: NumberPicker): String {
        val ampm = ampmPicker.value
        val hour = hourPicker.value
        val minute = minutePicker.value

        val calculatedHour = if (ampm == 1) {
            if (hour == 12) 12 else hour + 12
        } else {
            if (hour == 12) 0 else hour
        }

        return String.format("%02d:%02d", calculatedHour, minute)
    }
}