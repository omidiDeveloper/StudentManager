package com.example.studentmanagermvcandrxjava.model.local.student

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StudentDao {

    @Query("SELECT * FROM student")
    fun getAll() : LiveData<List<Student>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrUpdate(student: Student)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(student: List<Student>)

    @Query("DELETE FROM student WHERE id = :studentId")
    fun delete(studentId : Long?)
}