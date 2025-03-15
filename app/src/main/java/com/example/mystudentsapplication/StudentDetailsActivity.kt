package com.example.mystudentsapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mystudentsapplication.model.Student

class StudentDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_student_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val student = getStudentDataFromIntent()
        setStudentDetails(student)
        setEditButtonClickListener(student)
    }

    private fun getStudentDataFromIntent() :Student? {
        val student: Student? = intent.getParcelableExtra("student")
        return student
    }

    private fun setStudentDetails(student: Student?) {
        val nameTextView: TextView = findViewById(R.id.student_details_activity_fullname_text_view_value)
        val idTextView: TextView = findViewById(R.id.student_details_activity_id_text_view_value)
        val phoneTextView: TextView = findViewById(R.id.student_details_activity_phone_text_view_value)
        val addressTextView: TextView = findViewById(R.id.student_details_activity_address_text_view_value)
        val checkedCheckbox: CheckBox = findViewById(R.id.student_details_activity_checked_checkbox)

        nameTextView.text = student?.fullname
        idTextView.text = student?.id
        phoneTextView.text = student?.phone
        addressTextView.text = student?.address
        checkedCheckbox.isChecked = student?.isChecked ?: false
    }

    private  fun setEditButtonClickListener(student: Student?) {
        val editStudent: Button = findViewById(R.id.student_details_activity_edit_student_button)
        editStudent.setOnClickListener {
            val intent = Intent(applicationContext, EditStudentActivity::class.java)
            intent.putExtra("student", student)
            startActivity(intent)
        }
    }
}