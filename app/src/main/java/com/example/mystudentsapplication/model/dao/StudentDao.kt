package com.example.mystudentsapplication.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mystudentsapplication.model.Student

// NOTE: keeping this unused code. was approved by tal zion on 20/2 zoom meeting
@Dao
interface StudentDao {
    @Query("SELECT * FROM Student")
    fun getAllStudents(): List<Student>

    @Query("SELECT * FROM Student WHERE id =:id")
    fun getStudentById(id: String): Student

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStudents(vararg students: Student)

    @Delete
    fun delete(student: Student)

    @Query("""
        UPDATE Student 
        SET id = :newId, 
            fullname = :fullname, 
            phone = :phone, 
            address = :address, 
            isChecked = :isChecked 
        WHERE id = :oldId
    """)
    fun updateStudent(
        oldId: String,
        newId: String,
        fullname: String?,
        phone: String?,
        address: String?,
        isChecked: Boolean
    )

    @Query("UPDATE Student SET isChecked = :isChecked WHERE id = :id")
    fun updateIsCheckedById(id: String, isChecked: Boolean)

}