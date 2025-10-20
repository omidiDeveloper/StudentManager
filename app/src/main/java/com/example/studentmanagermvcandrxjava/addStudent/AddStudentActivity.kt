package com.example.studentmanagermvcandrxjava.addStudent

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.studentmanagermvcandrxjava.R
import com.example.studentmanagermvcandrxjava.databinding.ActivityMain2Binding
import com.example.studentmanagermvcandrxjava.model.MainRepository
import com.example.studentmanagermvcandrxjava.model.local.MyDatabase
import com.example.studentmanagermvcandrxjava.model.local.student.Student
import com.example.studentmanagermvcandrxjava.utils.AddStudentViewModelFactory
import com.example.studentmanagermvcandrxjava.utils.ApiServiceSingleTon
import com.example.studentmanagermvcandrxjava.utils.asyncRequest
import com.example.studentmanagermvcandrxjava.utils.showDialog
import com.example.studentmanagermvcandrxjava.utils.showToast
import io.reactivex.CompletableObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

@Suppress("DEPRECATION")
class AddStudentActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private lateinit var addStudentViewModel: AddStudentViewModel
    private val composDispose = CompositeDisposable()
    private var isInserting = true


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

        addStudentViewModel = ViewModelProvider(
            this, AddStudentViewModelFactory(
                (MainRepository(
                    ApiServiceSingleTon.apiService!!,
                    MyDatabase.getDatabase(applicationContext).studentDao
                ))
            )
        ).get(AddStudentViewModel::class.java)

        setSupportActionBar(binding.toolbarMain2)
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        binding.edtFirstName.requestFocus()


        val testMode = intent.getParcelableExtra<Student>("student")
        isInserting = (testMode == null)

        if (!isInserting) {
            uiLogicUpdateStudent()
        }

        binding.btnDone.setOnClickListener {

            if (isInserting) {
                addNewStudent()
            } else {
                updateStudent()
            }

        }

    }
    override fun onDestroy() {
        composDispose.clear()
        super.onDestroy()
    }

    private fun uiLogicUpdateStudent() {
        binding.btnDone.text = "update"

        val dataFromIntent = intent.getParcelableExtra<Student>("student")!!
        binding.edtScore.setText(dataFromIntent.score.toString())
        binding.edtCourse.setText(dataFromIntent.course)

        val splitedName = dataFromIntent.name.split(" ")
        binding.edtFirstName.setText(splitedName[0])
        binding.edtLastName.setText(splitedName[(splitedName.size - 1)])
    }
    private fun updateStudent() {

        val studentId = intent.getLongExtra("STUDENT_ID_TO_EDIT", -1)
        if (studentId == -1L) {
            showToast("Invalid Id")
            return
        }

        val firstName = binding.edtFirstName.text.toString()
        val lastName = binding.edtLastName.text.toString()
        val score = binding.edtScore.text.toString()
        val course = binding.edtCourse.text.toString()


        if (firstName.isNotEmpty() && lastName.isNotEmpty() && course.isNotEmpty() && score.isNotEmpty()) {

            addStudentViewModel.updateStudent(
                studentId, Student(studentId, firstName + " " + lastName, course, score.toInt())
            ).asyncRequest().subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {
                        composDispose.add(d)
                    }

                    override fun onComplete() {
                        showToast("update finished!")
                        onBackPressed()
                    }

                    override fun onError(e: Throwable) {
                        showDialog(e.message ?: "null", this@AddStudentActivity, "Error : ")
                    }

                })
        } else {
            showToast("لطفا اطلاعات را کامل وارد کنید")
        }

    }
    private fun addNewStudent() {

        val name = binding.edtFirstName.text.toString()
        val family = binding.edtLastName.text.toString()
        val course = binding.edtCourse.text.toString()
        val scoreText = binding.edtScore.text.toString()

        if (name.isNotEmpty() && course.isNotEmpty() && scoreText.isNotEmpty()) {

            addStudentViewModel.insertStudent(
                    Student(null, name + " " + family, course, scoreText.toInt())
                ).asyncRequest().subscribe(object : CompletableObserver {
                    override fun onSubscribe(d: Disposable) {
                        composDispose.add(d)
                    }

                    override fun onComplete() {
                        showToast("student added")
                        onBackPressed()
                    }

                    override fun onError(e: Throwable) {
                        showDialog(e.message ?: "null", this@AddStudentActivity, "Error : ")
                    }

                })

        } else {
            showToast("لطفا تمام فیلد هارا پر کنید :) ")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return true
    }
}