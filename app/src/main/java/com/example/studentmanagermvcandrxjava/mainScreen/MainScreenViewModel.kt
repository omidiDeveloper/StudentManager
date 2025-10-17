package com.example.studentmanagermvcandrxjava.mainScreen

import com.example.studentmanagermvcandrxjava.model.MainRepository
import com.example.studentmanagermvcandrxjava.model.Student
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit


class MainScreenViewModel (
    private val mainRepository: MainRepository
) {
    val progressBarSubject = BehaviorSubject.create<Boolean>()

    fun getAllStudents() : Single<List<Student>>{
        progressBarSubject.onNext(true)
        return mainRepository.getAllStudent()
            .delay(1 , TimeUnit.SECONDS)
            .doFinally {
                progressBarSubject.onNext(false)
            }
    }

    fun deleteStudent(studentId : Long?) : Completable{
        return mainRepository.deleteStudent(studentId)
    }

}