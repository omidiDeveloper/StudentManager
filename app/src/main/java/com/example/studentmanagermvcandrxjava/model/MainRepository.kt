package com.example.studentmanagermvcandrxjava.model

import com.example.studentmanagermvcandrxjava.utils.Constance
import com.example.studentmanagermvcandrxjava.utils.studentJsonObject
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MainRepository {
    private var apiService: ApiService

    init {

        val retrofit = Retrofit
            .Builder()
            .baseUrl(Constance.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)

    }

    fun getAllStudent() : Single<List<Student>>{
        return apiService.getAllStudents()
    }

    fun deleteStudent(studentId: Long?) : Completable{
        return apiService.deleteStudent(studentId!!)
    }

    fun insertStudent( student: Student) : Completable{
        return apiService.insertStudent(studentJsonObject(student))
    }

    fun updateStudent(studentId: Long? , student: Student) : Completable{
        return apiService.updateStudent(studentId!! , studentJsonObject(student) )
    }
}