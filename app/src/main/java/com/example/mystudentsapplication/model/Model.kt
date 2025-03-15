package com.example.mystudentsapplication.model

import android.os.Looper
import androidx.core.os.HandlerCompat
import java.util.concurrent.Executors

class Model private constructor() {

    private val studentsList = mutableListOf<Student>()
    private val executor = Executors.newSingleThreadExecutor()
    private val mainHandler = HandlerCompat.createAsync(Looper.getMainLooper())

    companion object {
        val shared = Model()
    }

    fun getAllStudents(callback: (List<Student>) -> Unit) {
        executor.execute {
            val students = studentsList.toList()
            mainHandler.post { callback(students) }
        }
    }

    fun addStudent(student: Student, callback: () -> Unit) {
        executor.execute {
            studentsList.add(student)
            mainHandler.post { callback() }
        }
    }

    fun updateStudent(id: String, student: Student, callback: () -> Unit) {
        executor.execute {
            val index = studentsList.indexOfFirst { it.id == id }
            if (index != -1) {
                studentsList[index] = student
            }
            mainHandler.post { callback() }
        }
    }

    fun deleteStudent(student: Student, callback: () -> Unit) {
        executor.execute {
            studentsList.remove(student)
            mainHandler.post { callback() }
        }
    }

    fun updateStudentIsCheckedById(id: String, isChecked: Boolean) {
        executor.execute {
            val student = studentsList.find { it.id == id }
            if (student != null) {
                student.isChecked = isChecked
            }
        }
    }
}
