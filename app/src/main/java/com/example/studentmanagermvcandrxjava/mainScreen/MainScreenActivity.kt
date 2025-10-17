package com.example.studentmanagermvcandrxjava.mainScreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.example.studentmanagermvcandrxjava.addStudent.AddStudentActivity
import com.example.studentmanagermvcandrxjava.databinding.ActivityMainBinding
import com.example.studentmanagermvcandrxjava.model.MainRepository
import com.example.studentmanagermvcandrxjava.model.Student
import com.example.studentmanagermvcandrxjava.utils.asyncRequest
import com.example.studentmanagermvcandrxjava.utils.showDialog
import com.example.studentmanagermvcandrxjava.utils.showToast
import io.reactivex.CompletableObserver
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class MainScreenActivity : AppCompatActivity(), StudentAdapter.StudentEvent {
    lateinit var binding: ActivityMainBinding
    lateinit var myAdapter: StudentAdapter
    private val compositeDispose = CompositeDisposable()
    lateinit var mainScreenViewModel: MainScreenViewModel

    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarMain)
        mainScreenViewModel = MainScreenViewModel(MainRepository())

        binding.btnAddStudent.setOnClickListener {
            val intent = Intent(this, AddStudentActivity::class.java)
            startActivity(intent)
        }

        compositeDispose.add(
            mainScreenViewModel.progressBarSubject
                .subscribe{
                    if (it){
                        runOnUiThread {
                            binding.progressBarMain.visibility = View.VISIBLE
                            binding.recyclerMain.visibility = View.INVISIBLE
                        }
                    }else {
                        runOnUiThread {
                            binding.progressBarMain.visibility = View.INVISIBLE
                            binding.recyclerMain.visibility = View.VISIBLE
                        }
                }
            }
        )


    }
    override fun onResume() {
        super.onResume()
        mainScreenViewModel
            .getAllStudents()
            .asyncRequest()
            .subscribe( object : SingleObserver<List<Student>>{
                override fun onSubscribe(d: Disposable) {
                    compositeDispose.add(d)
                }

                override fun onSuccess(t: List<Student>) {
                    setDataToRecycler(t)
                }

                override fun onError(e: Throwable) {
                    showDialog(e.message!! , this@MainScreenActivity , "Error : ")
                }
            })
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

        myAdapter.removeItem(student , position)
    }
    private fun setDataToRecycler(data: List<Student>) {
        val myData = ArrayList(data)
        myAdapter = StudentAdapter(myData, this)
        binding.recyclerMain.adapter = myAdapter
        binding.recyclerMain.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

}