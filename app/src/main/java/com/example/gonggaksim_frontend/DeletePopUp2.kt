package com.example.gonggaksim_frontend

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button

class DeletePopUp2(context: Context, private val onConfirm: (Boolean) -> Unit) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_delete_popup_3)
        window?.setBackgroundDrawableResource(android.R.color.transparent)

        val cancelButton = findViewById<Button>(R.id.dp3_no)
        val confirmButton = findViewById<Button>(R.id.dp3_yes)

        cancelButton.setOnClickListener {
            dismiss()
        }

        confirmButton.setOnClickListener {
            onConfirm(true)
            dismiss()
        }
    }
}
