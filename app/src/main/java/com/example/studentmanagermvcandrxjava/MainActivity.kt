    package com.example.studentmanagermvcandrxjava

    import android.annotation.SuppressLint
    import android.content.Intent
    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import android.util.Log
    import androidx.recyclerview.widget.LinearLayoutManager
    import androidx.recyclerview.widget.RecyclerView
    import cn.pedant.SweetAlert.SweetAlertDialog
    import com.example.studentmanagermvcandrxjava.databinding.ActivityMainBinding
    import com.example.studentmanagermvcandrxjava.net.ApiService
    import com.example.studentmanagermvcandrxjava.recycler.Student
    import com.example.studentmanagermvcandrxjava.recycler.StudentAdapter
    import retrofit2.*
    import retrofit2.converter.gson.GsonConverterFactory

    const val BASE_URL = "http://192.168.163.1:8080"

    class MainActivity : AppCompatActivity(), StudentAdapter.StudentEvent {
        lateinit var binding: ActivityMainBinding
        lateinit var myAdapter: StudentAdapter
        lateinit var apiService: ApiService

        @SuppressLint("UnspecifiedImmutableFlag")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)
            setSupportActionBar(binding.toolbarMain)



            val retrofit = Retrofit
                .Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            apiService = retrofit.create(ApiService::class.java)

            binding.btnAddStudent.setOnClickListener {
                val intent = Intent(this, MainActivity2::class.java)
                startActivity(intent)
            }


        }

        override fun onResume() {
            super.onResume()
            getDataFromApi()
        }

        fun getDataFromApi() {
            apiService.getAllStudents().enqueue(object : Callback<List<Student>> {
                override fun onResponse(
                    call: Call<List<Student>>,
                    response: Response<List<Student>>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val getData = response.body()!!
                        setDataToRecycler(getData)
                    } else {
                        Log.e("testLog", "Response is null or not successful: ${response.code()}")
                        SweetAlertDialog(this@MainActivity, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Error")
                            .setContentText("No data received from server.")
                            .show()
                    }
                }

                override fun onFailure(call: Call<List<Student>>, t: Throwable) {
                    Log.e("testLog", "API call failed: ${t.message}")
                    SweetAlertDialog(this@MainActivity, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Network Error")
                        .setContentText(t.message ?: "Unknown error")
                        .show()
                }
            })
        }

        fun deleteDataFromServer(student: Student, position: Int) {
            apiService.deleteStudent(student.id!!).enqueue(object : Callback<String>{
                override fun onResponse(
                    call: Call<String?>,
                    response: Response<String?>,
                ) {

                }

                override fun onFailure(call: Call<String?>, t: Throwable) {

                }

            })

            myAdapter.removeItem(student , position)
        }

        fun setDataToRecycler(data: List<Student>) {
            val myData = ArrayList(data)
            myAdapter = StudentAdapter(myData, this)
            binding.recyclerMain.adapter = myAdapter
            binding.recyclerMain.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        }

        override fun onItemClicked(student: Student, position: Int) {
            val intent = Intent(this , MainActivity2::class.java)
            intent.putExtra("student" , student)
            intent.putExtra("STUDENT_ID_TO_EDIT" , student.id)
            startActivity(intent)
        }

        override fun onItemLongClicked(student: Student, position: Int) {
            val dialog = SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
            dialog.contentText = "Delete this Item?"
            dialog.cancelText = "cancel"
            dialog.confirmText = "confirm"
            dialog.setOnCancelListener {
                dialog.dismiss()
            }
            dialog.setConfirmClickListener {

                deleteDataFromServer(student, position)
                dialog.dismiss()

            }
            dialog.show()
        }



    }