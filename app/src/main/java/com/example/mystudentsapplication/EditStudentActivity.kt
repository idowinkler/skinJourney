package com.example.mystudentsapplication

import android.content.Intent
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

class EditStudentActivity : AppCompatActivity() {

    var studentId: String = ""

    private fun goToStudentsListActiviy()  {
        val intent = Intent(this, StudentsRecyclerViewActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_student)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val student = getStudentDataFromIntent()
        setEditableStudentDetails(student)

        val cancelButton: Button = findViewById(R.id.edit_student_activity_cancel_button)
        val saveButton: Button = findViewById(R.id.edit_student_activity_save_button)
        val deleteButton: Button = findViewById(R.id.edit_student_activity_delete_button)

        cancelButton.setOnClickListener {
            finish()
        }

        saveButton.setOnClickListener {
            saveEditedStudent()
            goToStudentsListActiviy()
            finish()
        }

        deleteButton.setOnClickListener {
            if (student != null) {
                Model.shared.deleteStudent(student) {
                    goToStudentsListActiviy()
                    finish()
                }
            }
        }
    }

    private fun getStudentDataFromIntent() :Student? {
        val student: Student? = intent.getParcelableExtra("student")
        return student
    }

    private fun setEditableStudentDetails(student: Student?) {
        val nameTextView: TextView = findViewById(R.id.edit_student_activity_fullname_edit_text)
        val idTextView: TextView = findViewById(R.id.edit_student_activity_id_edit_text)
        val phoneTextView: TextView = findViewById(R.id.edit_student_activity_phone_edit_text)
        val addressTextView: TextView = findViewById(R.id.edit_student_activity_address_edit_text)
        val checkedCheckbox: CheckBox = findViewById(R.id.edit_student_activity_checked_checkbox)

        nameTextView.text = student?.fullname
        idTextView.text = student?.id
        phoneTextView.text = student?.phone
        addressTextView.text = student?.address
        checkedCheckbox.isChecked = student?.isChecked ?: false

        studentId = student?.id ?: ""
    }

    private fun saveEditedStudent () {
        val nameTextView: TextView = findViewById(R.id.edit_student_activity_fullname_edit_text)
        val idTextView: TextView = findViewById(R.id.edit_student_activity_id_edit_text)
        val phoneTextView: TextView = findViewById(R.id.edit_student_activity_phone_edit_text)
        val addressTextView: TextView = findViewById(R.id.edit_student_activity_address_edit_text)
        val checkedCheckbox: CheckBox = findViewById(R.id.edit_student_activity_checked_checkbox)

        val student = Student(
            id = idTextView.text?.toString() ?: "",
            fullname = nameTextView.text?.toString() ?: "",
            phone = phoneTextView.text?.toString() ?: "",
            address = addressTextView.text?.toString() ?: "",
            isChecked = checkedCheckbox.isChecked
        )

        Model.shared.updateStudent(studentId, student) {
            finish()
        }
    }
}