package com.example.studentmanagermvcandrxjava.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.studentmanagermvcandrxjava.addStudent.AddStudentViewModel
import com.example.studentmanagermvcandrxjava.mainScreen.MainScreenViewModel
import com.example.studentmanagermvcandrxjava.model.MainRepository

class MainViewModelFactory(private val mainRepository: MainRepository)
    : ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainScreenViewModel(mainRepository) as T
    }
}


class AddStudentViewModelFactory(private val mainRepository: MainRepository) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddStudentViewModel(mainRepository) as T
    }
}