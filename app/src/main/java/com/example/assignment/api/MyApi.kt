package com.example.assignment.api

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface MyApi {
    @GET("list/all")
    fun dogslist(): Call<ResponseBody>

    companion object{
        operator fun invoke(
        ): MyApi {
            return Retrofit.Builder()
                .baseUrl("https://dog.ceo/api/breeds/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }
}