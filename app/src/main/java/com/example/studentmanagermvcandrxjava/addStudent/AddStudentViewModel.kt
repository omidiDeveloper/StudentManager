package com.example.studentmanagermvcandrxjava.addStudent

import com.example.studentmanagermvcandrxjava.model.MainRepository
import com.example.studentmanagermvcandrxjava.model.Student
import io.reactivex.Completable

class AddStudentViewModel(
    private val mainRepository: MainRepository
) {

    fun insertStudent(student: Student) : Completable{
        return mainRepository.insertStudent(student)
    }

    fun updateStudent(studentId : Long? , student: Student) : Completable{
        return mainRepository.updateStudent(studentId , student)
    }

}