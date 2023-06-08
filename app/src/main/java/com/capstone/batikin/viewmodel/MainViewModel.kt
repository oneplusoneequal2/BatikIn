package com.capstone.batikin.viewmodel

import android.content.Context
import androidx.compose.runtime.MutableState
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.batikin.api.ApiConfig
import com.capstone.batikin.api.response.*
import com.capstone.batikin.model.*
import com.capstone.batikin.model.preference.UserPref
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.TypeVariable

class MainViewModel: ViewModel() {

    private var _listData = MutableLiveData<List<DataItem?>?>()
    val listData: LiveData<List<DataItem?>?> = _listData

    private var _detailData = MutableLiveData<Data>()
    val detailData: LiveData<Data> = _detailData

    private var _isLogin = MutableLiveData<Boolean>()
    val isLogin: LiveData<Boolean> = _isLogin

    private var _isRegister = MutableLiveData<Boolean>()
    val isRegister: LiveData<Boolean> = _isRegister

    private var _loginResponse = MutableLiveData<LoginResponse?>()
    val loginResponse: LiveData<LoginResponse?> = _loginResponse

    private var _registerResponse = MutableLiveData<RegisterResponse?>()
    val registerResponse: LiveData<RegisterResponse?> = _registerResponse

    private var _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private var _wishlistData = MutableLiveData<List<DataWishlist>>()
    val wishlistData: LiveData<List<DataWishlist>> = _wishlistData

    private val userState = UserState()

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
        client.enqueue(object : Callback<BatikDetailResponse>{
            override fun onResponse(
                call: Call<BatikDetailResponse>,
                response: Response<BatikDetailResponse>
            ) {
                _isLoading.value = false
                _detailData.postValue(response.body()?.data)
            }

            override fun onFailure(call: Call<BatikDetailResponse>, t: Throwable) {
                _isLoading.value = false
            }

        })
    }

    fun getWishlist(token: String, id: Int) {
        val client = ApiConfig.getApiService().getWishlist(token, id)
        client.enqueue(object : Callback<WishlistResponse>{
            override fun onResponse(
                call: Call<WishlistResponse>,
                response: Response<WishlistResponse>
            ) {
                _wishlistData.postValue(response.body()?.data)
            }

            override fun onFailure(call: Call<WishlistResponse>, t: Throwable) {

            }

        })
    }

    fun addWishlist(token: String, id: Int, photoUrl: String, price: Int, title: String) {

        val client = ApiConfig.getApiService().postWishList(token, id, photoUrl, price, title)
        client.enqueue(object : Callback<WishlistResponse> {
            override fun onResponse(call: Call<WishlistResponse>, response: Response<WishlistResponse>) {
                val responseBody = response.body()
                if (responseBody != null) {
                    _wishlistData.postValue(responseBody.data)
                    getWishlist(token, id)

                }
            }

            override fun onFailure(call: Call<WishlistResponse>, t: Throwable) {

            }
        })
    }


    fun checkLogin(context: Context, email: String, password: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().login(email, password)
        client.enqueue(object : Callback<LoginResponse>{
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                val responseBody = response.body()
                _isLoading.value = false
                if (responseBody?.status == 200) {
                    _isLogin.postValue(true)
                    _loginResponse.postValue(responseBody)
                    setStateLogin(context, true, responseBody)
                } else {
                    _isLogin.postValue(false)
                    _loginResponse.postValue(responseBody)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
            }

        })
    }

    fun logout(context: Context) {
        setStateLogout(context, false, "", -1, "")
    }

    fun registerData(name: String, email: String, password: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().register(name, email, password)
        client.enqueue(object: Callback<RegisterResponse>{
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                _isLoading.value = false
                val responseBody = response.body()
                if (responseBody?.status == 201) {
                    _isRegister.postValue(true)
                    _registerResponse.postValue(responseBody)
                } else {
                    _isRegister.postValue(false)
                    _registerResponse.postValue(responseBody)
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
            }

        })
    }

    private fun setStateLogin(context: Context, isLogin: Boolean, response: LoginResponse) {
        val userPref = UserPref(context)

        userState.isLogin = isLogin
        userState.name = response.data.userName
        userState.token = response.data.token
        userState.id = response.data.userId

        userPref.setState(userState)
    }

    private fun setStateLogout(context: Context, isLogin: Boolean, name: String, id: Int, token:String) {
        val userPref = UserPref(context)

        userState.isLogin = isLogin
        userState.name = name
        userState.token = token
        userState.id = id

        userPref.setState(userState)
    }

}