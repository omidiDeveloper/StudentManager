package com.example.studentmanagermvcandrxjava.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import cn.pedant.SweetAlert.SweetAlertDialog
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


fun Context.showToast(str : String){
    Toast.makeText(this, str, Toast.LENGTH_SHORT).show()
}

fun Context.showDialog(msg : String , activity: Activity , title : String){
    SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
        .setTitleText(title)
        .setContentText("Error : ${msg}")
        .show()
}

@SuppressLint("CheckResult")
fun <T> Single<T>.asyncRequest() : Single<T>{
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

@SuppressLint("CheckResult")
fun Completable.asyncRequest() : Completable{
    return subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}
