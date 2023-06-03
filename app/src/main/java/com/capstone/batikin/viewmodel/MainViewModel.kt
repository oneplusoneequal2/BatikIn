package com.capstone.batikin.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.batikin.api.ApiConfig
import com.capstone.batikin.api.response.BatikResponse
import com.capstone.batikin.api.response.DataItem
import com.capstone.batikin.api.response.LoginResponse
import com.capstone.batikin.api.response.RegisterResponse
import com.capstone.batikin.model.Batik
import com.capstone.batikin.model.emailDummy
import com.capstone.batikin.model.listDummy
import com.capstone.batikin.model.passDummy
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.TypeVariable

class MainViewModel: ViewModel() {

    private var _listData = MutableLiveData<List<DataItem?>?>()
    val listData: LiveData<List<DataItem?>?> = _listData

    private var _detailData = MutableLiveData<DataItem?>()
    val detailData: LiveData<DataItem?> = _detailData

    private var _isLogin = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean> = _isLogin

    private var _isRegister = MutableLiveData<Boolean>()
    val isRegister: LiveData<Boolean> = _isRegister

    private var _response = MutableLiveData<String?>()
    val response: LiveData<String?> = _response

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getBatikList() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getBatikList()
        client.enqueue(object: Callback<BatikResponse>{
            override fun onResponse(call: Call<BatikResponse>, response: Response<BatikResponse>) {
                _isLoading.value = false
                val responseBody = response.body()
                _listData.postValue(responseBody?.data)
            }

            override fun onFailure(call: Call<BatikResponse>, t: Throwable) {
                _isLoading.value = false
            }
        })
    }

    fun getBatikDetail(id: Int) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getBatikDetail(id)
        client.enqueue(object : Callback<DataItem>{
            override fun onResponse(call: Call<DataItem>, response: Response<DataItem>) {
                _isLoading.value = false
                val responseBody = response.body()
                _detailData.postValue(responseBody)
            }

            override fun onFailure(call: Call<DataItem>, t: Throwable) {
                _isLoading.value = false
            }
        })
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

    fun registerData(name: String, email: String, password: String) {
        val client = ApiConfig.getApiService().register(name, email, password)
        client.enqueue(object: Callback<RegisterResponse>{
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                val responseBody = response.body()
                if (responseBody?.status == 201) {
                    _isRegister.postValue(true)
                    _response.postValue(responseBody.message)
                } else {
                    _isRegister.postValue(false)
                    _response.postValue(responseBody?.message)
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {

            }

        })
    }

}