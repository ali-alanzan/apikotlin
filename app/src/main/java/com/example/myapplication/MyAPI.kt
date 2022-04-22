package com.example.myapplication

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface MyAPI {

    @Multipart
    @POST("/api/v1/imagesearch/upload")
    fun uploadImage(
        @Part image: MultipartBody.Part,
    ) : Call<String>

    companion object {
        operator fun invoke() : MyAPI {
            return Retrofit.Builder()
                .baseUrl("https://api-edu.gtl.ai")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyAPI::class.java)
        }
    }
}

