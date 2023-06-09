package com.capstone.batikin.api

import com.capstone.batikin.api.response.*
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @GET("batik")
    fun getBatikList(): Call<BatikResponse>

    @GET("batik/{id}")
    fun getBatikDetail(
        @Path("id") id: Int
    ): Call<BatikDetailResponse>

    @GET("wishlist/{id}")
    fun getWishlist(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Call<WishlistResponse>

    @FormUrlEncoded
    @POST("wishlist/{batikId}")
    fun postWishList(
        @Header("Authorization") token: String,
        @Path("batikId") id: Int,
        @Field("photoUrl") photoUrl: String,
        @Field("price") price: Int,
        @Field("title") title: String,
    ): Call<WishlistResponse>

    @DELETE("wishlist/{batikId}")
    fun deleteWishlist(
        @Header("Authorization") token: String,
        @Path("batikId") id: Int,
    ): Call<WishlistResponse>
}