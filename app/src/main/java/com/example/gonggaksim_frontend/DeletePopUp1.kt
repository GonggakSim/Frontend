package com.example.gonggaksim_frontend

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class DeletePopUp1(
    context: Context,
    private val onDeleteConfirmed: (Boolean) -> Unit
) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_delete_popup)
        window?.setBackgroundDrawableResource(android.R.color.transparent)

        val cancelButton = findViewById<Button>(R.id.dp1_btn_no)
        val deleteButton = findViewById<Button>(R.id.dp1_btn_yes)

        cancelButton.setOnClickListener {
            dismiss()
        }

        deleteButton.setOnClickListener {
            dismiss()
            val confirmDialog = DeletePopUp2(context) { isConfirmed ->
                if (isConfirmed) {
                    onDeleteConfirmed(true)
                }
            }
            confirmDialog.show()
        }
    }
}


