package com.example.studentmanagermvcandrxjava.utils

import com.example.studentmanagermvcandrxjava.model.Student
import com.google.gson.JsonObject

fun studentJsonObject(student: Student) : JsonObject{
    val jsonObject = JsonObject()
    jsonObject.addProperty("id" , student.id)
    jsonObject.addProperty("name", student.name)
    jsonObject.addProperty("course", student.course)
    jsonObject.addProperty("score", student.score)

    return jsonObject
}