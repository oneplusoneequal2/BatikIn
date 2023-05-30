package com.capstone.batikin.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.batikin.api.ApiConfig
import com.capstone.batikin.api.response.LoginResponse
import com.capstone.batikin.model.Batik
import com.capstone.batikin.model.emailDummy
import com.capstone.batikin.model.listDummy
import com.capstone.batikin.model.passDummy
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.TypeVariable

class MainViewModel: ViewModel() {

    private var _listData = MutableLiveData<ArrayList<Batik>>()
    val listData: LiveData<ArrayList<Batik>> = _listData

    private var _detailData = MutableLiveData<Batik>()
    val detailData: LiveData<Batik> = _detailData

    private var _isLogin = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean> = _isLogin

    private var _response = MutableLiveData<String?>()
    val response: LiveData<String?> = _response

    fun getData() {
        _listData.postValue(listDummy)
    }

    fun getDataDetail(id: Int) {
        _detailData.postValue(listDummy.find { it.id == id })
    }

    fun checkLogin(email: String, password: String) {
        val client = ApiConfig.getApiService().login(email, password)
        client.enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val responseBody = response.body()
                if (responseBody?.status == 200) {
                    _isLogin.postValue(true)
                    _response.postValue(responseBody.message)
                } else {
                    _isLogin.postValue(false)
                    _response.postValue(responseBody?.message)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {

            }

        })
    }

}