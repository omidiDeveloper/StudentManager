package com.example.studentmanagermvcandrxjava.addStudent

import androidx.lifecycle.ViewModel
import com.example.studentmanagermvcandrxjava.model.MainRepository
import com.example.studentmanagermvcandrxjava.model.local.student.Student
import io.reactivex.Completable

class AddStudentViewModel(
    private val mainRepository: MainRepository
) : ViewModel(){

    fun insertStudent(student: Student) : Completable{
        return mainRepository.insertStudent(student)
    }

    fun updateStudent(studentId : Long? , student: Student) : Completable{
        return mainRepository.updateStudent(studentId , student)
    }

}