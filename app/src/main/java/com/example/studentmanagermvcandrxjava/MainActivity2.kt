package com.example.studentmanagermvcandrxjava

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.studentmanagermvcandrxjava.databinding.ActivityMain2Binding
import com.example.studentmanagermvcandrxjava.net.ApiService
import com.example.studentmanagermvcandrxjava.recycler.Student
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

@Suppress("DEPRECATION")
class MainActivity2 : AppCompatActivity() {
    lateinit var binding : ActivityMain2Binding
    lateinit var apicService: ApiService
    var isInserting = true


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMain2Binding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainView)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setSupportActionBar(binding.toolbarMain2)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.edtFirstName.requestFocus()

        val retrofit = Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        apicService = retrofit.create(ApiService::class.java)

        val testMode = intent.getParcelableExtra<Student>("student")
        isInserting = (testMode == null)

        if (!isInserting) {

            binding.btnDone.text = "update"

            val dataFromIntent = intent.getParcelableExtra<Student>("student")!!
            binding.edtScore.setText(dataFromIntent.score.toString())
            binding.edtCourse.setText(dataFromIntent.course)

            val splitedName = dataFromIntent.name.split(" ")
            binding.edtFirstName.setText(splitedName[0])
            binding.edtLastName.setText(splitedName[(splitedName.size - 1)])

        }

        binding.btnDone.setOnClickListener {

            if (isInserting) {
                addNewStudent()
            } else {
                updateStudent()
            }

        }

    }

    private fun updateStudent() {

        val studentId = intent.getLongExtra("STUDENT_ID_TO_EDIT", -1)
        if (studentId == -1L) {
            Toast.makeText(this, "Invalid student ID", Toast.LENGTH_SHORT).show()
            return
        }
        val firstName = binding.edtFirstName.text.toString()
        val lastName = binding.edtLastName.text.toString()
        val score = binding.edtScore.text.toString()
        val course = binding.edtCourse.text.toString()


        if (
            firstName.isNotEmpty() &&
            lastName.isNotEmpty() &&
            course.isNotEmpty() &&
            score.isNotEmpty()
        ) {

            val jsonObject = JsonObject()
            jsonObject.addProperty("id" , studentId)
            jsonObject.addProperty("name", firstName + " " + lastName)
            jsonObject.addProperty("course", course)
            jsonObject.addProperty("score", score.toInt())


            apicService
                .updateStudent(studentId, jsonObject)
                .enqueue(object : Callback<String> {
                    override fun onResponse(
                        call: Call<String?>,
                        response: Response<String?>,
                    ) {
                        Log.v("updateUser" , response.body().toString())
                    }

                    override fun onFailure(
                        call: Call<String?>,
                        t: Throwable,
                    ) {
                        Log.v("updateUser" , t.message!!)
                    }

                })

            Toast.makeText(this, "update finished!", Toast.LENGTH_SHORT).show()
            onBackPressed()


        } else {
            Toast.makeText(this, "لطفا اطلاعات را کامل وارد کنید", Toast.LENGTH_SHORT).show()
        }

    }

    private fun addNewStudent() {

        val name = binding.edtFirstName.text.toString()
        val family = binding.edtLastName.text.toString()
        val course = binding.edtCourse.text.toString()
        val scoreText = binding.edtScore.text.toString()

        if (name.isNotEmpty() && course.isNotEmpty() && scoreText.isNotEmpty()) {

            val score = scoreText.toIntOrNull() ?: 0

            val jsonObject = JsonObject()
            jsonObject.addProperty("name", name +  " " + family)
            jsonObject.addProperty("course", course)
            jsonObject.addProperty("score", score)

            apicService.insertStudent(jsonObject).enqueue(object : Callback<String> {
                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.v("sendLog", "Response code=${response.code()} body=${response.body()}")
                }

                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.e("sendLog", "Error: ${t.message}")
                }
            })

            onBackPressed()
        }else{
            Log.v("sendLog" , "لطفا تمام فیلد هارا پر کنید :) ")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home){
            onBackPressed()
        }
        return true
    }
}




