package com.capstone.batikin.api

import com.capstone.batikin.api.response.*
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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
        @Path("id") id: Int
    ): Call<WishlistResponse>

}