package com.example.studentmanagermvcandrxjava.model

import androidx.lifecycle.LiveData
import com.example.studentmanagermvcandrxjava.model.api.ApiService
import com.example.studentmanagermvcandrxjava.model.local.student.Student
import com.example.studentmanagermvcandrxjava.model.local.student.StudentDao
import com.example.studentmanagermvcandrxjava.utils.studentJsonObject
import io.reactivex.Completable


class MainRepository (
    private val apiService: ApiService,
    private val studentDao: StudentDao
) {

    fun getAllStudent() : LiveData<List<Student>>{
        return studentDao.getAll()
    }

    fun refreshData() : Completable{
        return apiService
            .getAllStudents()
            .doOnSuccess {
                studentDao.insertAll(it)
            }.ignoreElement()
    }

    fun insertStudent( student: Student) : Completable{
        return apiService
            .insertStudent(studentJsonObject(student))
            .doOnComplete {
                studentDao.insertOrUpdate(student)
            }
    }

    fun updateStudent(studentId: Long? , student: Student) : Completable{
        return apiService
            .updateStudent(studentId!! , studentJsonObject(student) )
            .doOnComplete {
                studentDao.insertOrUpdate(student)
            }
    }

    fun deleteStudent(studentId: Long?) : Completable{
        return apiService
            .deleteStudent(studentId!!)
            .doOnComplete {
                studentDao.delete(studentId)
            }
    }


}