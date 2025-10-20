package com.example.studentmanagermvcandrxjava.model.local.student

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "student")
data class Student(

    @PrimaryKey
    val id: Long?,

    val name: String,
    val course: String,
    val score: Int

) : Parcelable