package com.example.studentmanagermvcandrxjava.model

import com.google.gson.JsonObject
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {



    @GET("students")
    fun getAllStudents(): Single<List<Student>>

    @POST("students")
    fun insertStudent(@Body body: JsonObject) : Completable

    @PUT("students/updating/{id}")
    fun updateStudent(
        @Path("id") id: Long,
        @Body body: JsonObject
    ): Completable

    @DELETE("students/deleting{id}")
    fun deleteStudent(@Path("id") id: Long) : Completable
}