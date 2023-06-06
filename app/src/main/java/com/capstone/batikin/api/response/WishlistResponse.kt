package com.capstone.batikin.api.response

import com.google.gson.annotations.SerializedName

data class WishlistResponse(

	@field:SerializedName("data")
	val data: List<DataWishlist>,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: Int
)

data class DataWishlist(

	@field:SerializedName("photourl")
	val photourl: String,

	@field:SerializedName("price")
	val price: Int,

	@field:SerializedName("rating")
	val rating: Any,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String
)
