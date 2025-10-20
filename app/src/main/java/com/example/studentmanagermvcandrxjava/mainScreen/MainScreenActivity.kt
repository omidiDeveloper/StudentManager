package com.example.studentmanagermvcandrxjava.mainScreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.studentmanagermvcandrxjava.addStudent.AddStudentActivity
import com.example.studentmanagermvcandrxjava.databinding.ActivityMainBinding
import com.example.studentmanagermvcandrxjava.model.MainRepository
import com.example.studentmanagermvcandrxjava.model.local.MyDatabase
import com.example.studentmanagermvcandrxjava.model.local.student.Student
import com.example.studentmanagermvcandrxjava.utils.ApiServiceSingleTon
import com.example.studentmanagermvcandrxjava.utils.MainViewModelFactory
import com.example.studentmanagermvcandrxjava.utils.asyncRequest
import com.example.studentmanagermvcandrxjava.utils.showDialog
import com.example.studentmanagermvcandrxjava.utils.showToast
import io.reactivex.CompletableObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class MainScreenActivity : AppCompatActivity(), StudentAdapter.StudentEvent {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myAdapter: StudentAdapter
    private val compositeDispose = CompositeDisposable()
    private lateinit var mainScreenViewModel: MainScreenViewModel

    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarMain)

        mainScreenViewModel = ViewModelProvider(this,
            MainViewModelFactory(
                MainRepository(ApiServiceSingleTon.apiService!!,
                MyDatabase.getDatabase(applicationContext).studentDao
                )
            )
        ).get(MainScreenViewModel::class.java)

        mainScreenViewModel.getAllStudent().observe(this){
            refreshRecyclerData(it)
        }

        mainScreenViewModel.getErrorData().observe(this){
            Log.e("errorLog", it)
        }

        initRecycler()

        binding.btnAddStudent.setOnClickListener {
            val intent = Intent(this, AddStudentActivity::class.java)
            startActivity(intent)
        }




    }
    override fun onDestroy() {
        compositeDispose.clear()
        super.onDestroy()
    }

    override fun onItemClicked(student: Student, position: Int) {
        val intent = Intent(this, AddStudentActivity::class.java)
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

    private fun refreshRecyclerData(it: List<Student>) {
        myAdapter.refreshData(it)
    }
    private fun deleteDataFromServer(student: Student, position: Int) {
        mainScreenViewModel
            .deleteStudent(student.id)
            .asyncRequest()
            .subscribe( object : CompletableObserver{
                override fun onSubscribe(d: Disposable) {
                    compositeDispose.add(d)
                }

                override fun onComplete() {
                    showToast("Item Removed")
                }

                override fun onError(e: Throwable) {
                    showDialog(e.message!! ,this@MainScreenActivity , "Error : " )
                }

            })

    }
    private fun initRecycler() {
        val myData = arrayListOf<Student>()
        myAdapter = StudentAdapter(myData, this)
        binding.recyclerMain.adapter = myAdapter
        binding.recyclerMain.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

}