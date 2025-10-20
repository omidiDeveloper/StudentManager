package com.example.studentmanagermvcandrxjava.mainScreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studentmanagermvcandrxjava.model.MainRepository
import com.example.studentmanagermvcandrxjava.model.local.student.Student
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit


class MainScreenViewModel (
    private val mainRepository: MainRepository
) : ViewModel() {

    private lateinit var netDisposable : Disposable
    private val errorData = MutableLiveData<String>()


    init {
        mainRepository
            .refreshData()
            .subscribeOn(Schedulers.io())
            .subscribe( object : CompletableObserver{
                override fun onSubscribe(d: Disposable) {
                    netDisposable = d
                }

                override fun onComplete() {

                }

                override fun onError(e: Throwable) {
                    errorData.postValue(e.message ?:"unknown error")
                }

            })
    }

    fun getAllStudent() : LiveData<List<Student>>{
        return mainRepository.getAllStudent()
    }
    fun getErrorData() : LiveData<String>{
        return errorData
    }
    fun deleteStudent(studentId : Long?) : Completable{
        return mainRepository.deleteStudent(studentId)
    }

    override fun onCleared() {
        netDisposable.dispose()
        super.onCleared()
    }

}