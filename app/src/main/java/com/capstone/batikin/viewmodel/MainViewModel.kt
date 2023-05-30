package com.capstone.batikin.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.batikin.model.Batik
import com.capstone.batikin.model.emailDummy
import com.capstone.batikin.model.listDummy
import com.capstone.batikin.model.passDummy
import java.lang.reflect.TypeVariable

class MainViewModel: ViewModel() {

    private var _listData = MutableLiveData<ArrayList<Batik>>()
    val listData: LiveData<ArrayList<Batik>> = _listData

    private var _detailData = MutableLiveData<Batik>()
    val detailData: LiveData<Batik> = _detailData

    private var _isLogin = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean> = _isLogin

    fun getData() {
        _listData.postValue(listDummy)
    }

    fun getDataDetail(id: Int) {
        _detailData.postValue(listDummy.find { it.id == id })
    }

    fun checkLogin(email: String, password: String) {
        _isLogin.postValue(email == emailDummy && password == passDummy)
    }

}