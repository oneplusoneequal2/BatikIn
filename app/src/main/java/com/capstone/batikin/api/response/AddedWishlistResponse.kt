package com.capstone.batikin.api.response

import com.google.gson.annotations.SerializedName

data class AddedWishlistResponse(

	@field:SerializedName("data")
	val data: List<Any?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)