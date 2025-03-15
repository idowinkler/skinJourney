package com.example.mystudentsapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mystudentsapplication.OnItemClickListener
import com.example.mystudentsapplication.model.Student
import com.example.mystudentsapplication.R

class StudentsRecyclerAdapter(private var students: List<Student>?): RecyclerView.Adapter<StudentsViewHolder>() {
    var listener: OnItemClickListener? = null

    fun set(students: List<Student>?) {
        this.students = students
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentsViewHolder {
            val inflator = LayoutInflater.from(parent.context)
            val view = inflator.inflate(R.layout.student_list_row, parent, false)

            return StudentsViewHolder(view, listener)
        }

        override fun getItemCount(): Int = students?.size ?: 0

        override fun onBindViewHolder(holder: StudentsViewHolder, position: Int) {
            holder.bind(students?.get(position), position)
        }
}