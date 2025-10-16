package com.example.studentmanagermvcandrxjava.net

import com.example.studentmanagermvcandrxjava.recycler.Student
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("students")
    fun getAllStudents(): Call<List<Student>>

    @POST("students")
    fun insertStudent(@Body body: JsonObject): Call<String>

    @PUT("students/updating/{id}")
    fun updateStudent(
        @Path("id") id: Long,
        @Body body: JsonObject
    ): Call<String>

    @DELETE("students/deleting{id}")
    fun deleteStudent(@Path("id") id: Long) : Call<String>
}
