package com.example.mystudentsapplication

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mystudentsapplication.model.Model
import com.example.mystudentsapplication.model.Student

class AddStudentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add_students)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val saveButton: Button = findViewById(R.id.add_student_activity_save_button)
        val cancelButton: Button = findViewById(R.id.add_student_activity_cancel_button)

        saveButton.setOnClickListener {
            onSaveClicked()
        }


        cancelButton.setOnClickListener {
            finish()
        }
    }

    private fun onSaveClicked() {
        val nameTextView: TextView = findViewById(R.id.add_student_activity_username_edit_text)
        val idTextView: TextView = findViewById(R.id.add_student_activity_id_edit_text)
        val phoneTextView: TextView = findViewById(R.id.add_student_activity_phone_edit_text)
        val addressTextView: TextView = findViewById(R.id.add_student_activity_address_edit_text)
        val checkedCheckbox: CheckBox = findViewById(R.id.add_student_activity_checked_checkbox)

        val student = Student(
            id = idTextView.text?.toString() ?: "",
            fullname = nameTextView.text?.toString() ?: "",
            phone = phoneTextView.text?.toString() ?: "",
            address = addressTextView.text?.toString() ?: "",
            isChecked = checkedCheckbox.isChecked
        )

        Model.shared.addStudent(student) {
            finish()
        }
    }
}