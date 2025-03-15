package com.example.mystudentsapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mystudentsapplication.adapter.StudentsRecyclerAdapter
import com.example.mystudentsapplication.model.Model
import com.example.mystudentsapplication.model.Student

interface OnItemClickListener {
    fun onItemClick(student: Student?)
}

class StudentsRecyclerViewActivity : AppCompatActivity() {
    private var students: List<Student>? = null
    private var adapter: StudentsRecyclerAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_students_recycler_view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recyclerView: RecyclerView = findViewById(R.id.students_recycler_view)
        recyclerView.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        adapter = StudentsRecyclerAdapter(students)
        adapter?.listener = object : OnItemClickListener {
            override fun onItemClick(student: Student?) {
                val intent = Intent(applicationContext, StudentDetailsActivity::class.java)
                intent.putExtra("student", student)
                startActivity(intent)
            }
        }
        recyclerView.adapter = adapter

        val addStudentButton: Button = findViewById(R.id.students_recycler_view_activity_add_students_button)
        addStudentButton.setOnClickListener {
            val intent = Intent(this, AddStudentActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        Model.shared.getAllStudents {
            this.students = it
            adapter?.set(it)
            adapter?.notifyDataSetChanged()
        }
    }
}