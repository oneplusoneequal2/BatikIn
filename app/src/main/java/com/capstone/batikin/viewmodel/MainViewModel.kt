package com.capstone.batikin.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.batikin.model.Batik
import com.capstone.batikin.model.listDummy
import java.lang.reflect.TypeVariable

class MainViewModel: ViewModel() {
    private var _listData = MutableLiveData<ArrayList<Batik>>()
    val listData: LiveData<ArrayList<Batik>> = _listData

    fun getData() {
        _listData.postValue(listDummy)
    }

}