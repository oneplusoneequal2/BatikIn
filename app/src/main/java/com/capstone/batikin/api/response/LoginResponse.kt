package com.capstone.batikin.api.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("data")
	val data: LoginData,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)

data class LoginData(

	@field:SerializedName("userName")
	val userName: String,

	@field:SerializedName("userId")
	val userId: Int,

	@field:SerializedName("token")
	val token: String
)
