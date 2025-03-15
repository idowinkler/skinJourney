package com.example.mystudentsapplication.adapter

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mystudentsapplication.OnItemClickListener
import com.example.mystudentsapplication.model.Student
import com.example.mystudentsapplication.R
import com.example.mystudentsapplication.model.Model

class StudentsViewHolder(itemView: View, listener: OnItemClickListener?): RecyclerView.ViewHolder(itemView) {
        var fullnameTextView: TextView? = null
        var idTextView: TextView? = null
        var checkbox: CheckBox? = null
        var student: Student? = null

        init {
            fullnameTextView = itemView.findViewById(R.id.student_row_fullname_text_view)
            idTextView = itemView.findViewById(R.id.student_row_id_text_view)
            checkbox = itemView.findViewById(R.id.student_row_checked_checkbox)

            checkbox?.apply {
                setOnClickListener { view ->
                    (tag as Int)?.let { tag ->
                        val studentId = idTextView?.text?.toString() ?: ""
                        val isChecked = (view as? CheckBox)?.isChecked ?: false;
                        Model.shared.updateStudentIsCheckedById(studentId, isChecked)
                        student?.isChecked = (view as? CheckBox)?.isChecked ?: false
                    }
                }
            }

            itemView.setOnClickListener {
                listener?.onItemClick(student)
            }
        }

        fun bind(student: Student?, position: Int) {
            this.student = student
            fullnameTextView?.text = student?.fullname
            idTextView?.text = student?.id
            checkbox?.apply {
                isChecked = student?.isChecked ?: false
                tag = position
            }
        }
    }