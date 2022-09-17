package com.project.storyapps.api

import com.project.storyapps.model.ResponseData
import com.project.storyapps.model.ResponseRegister
import com.project.storyapps.model.ResponseStories
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("stories")
    fun allStories(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Part("size") size: Int
    ): Call<ResponseStories>

    @GET("stories")
    fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): Call<ResponseData>

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<ResponseData>

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): Call<ResponseRegister>
}