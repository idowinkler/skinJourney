package com.example.skinJourney.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skinJourney.OnItemClickListener
import com.example.skinJourney.model.Student
import com.example.skinJourney.R

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