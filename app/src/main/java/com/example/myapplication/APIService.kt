package com.example.myapplication

import com.google.gson.JsonObject
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import okhttp3.MultipartBody

import retrofit2.http.Body





interface APIService {


        // ...

        @POST("/api/v1/imagesearch/upload")
        suspend fun uploadEmployeeData( @Part file: MultipartBody.Part): Response<ResponseBody>

        @GET("/api/v1/imagesearch/bing")
        suspend fun getFromBing(@Query("url") url: String?): Response<ResponseBody>


        @GET("/api/v1/imagesearch/tineye")
        suspend fun getFromTineye(@Query("url") url: String?): Response<ResponseBody>

        @GET("/api/v1/imagesearch/google")
        suspend fun getFromGoogle(@Query("url") url: String?): Response<ResponseBody>











////////////////this is work ///////////////////////
   /*
    @Multipart
    @POST("/api/v1/imagesearch/upload")
    fun upload(
        @Part file: MultipartBody.Part
    ): Call<ResponseBody>*/
/////////////this is wok ///////////////////////

  /*  @Multipart
    @POST("/api/v1/imagesearch/upload")
    suspend fun uploadEmployeeDataa(@PartMap map: HashMap<String?, RequestBody?>): Response<ResponseBody>*/


   /*  @Multipart
    @POST("/api/v1/imagesearch/upload")
    suspend fun uploadEmployeeData(@Part body: MultipartBody.Part): Response<ResponseBody>*/



    // ...

   /* @Multipart
    @POST("/post")
    suspend fun uploadEmployeeDataa(@PartMap map: HashMap<String?, RequestBody?>): Response<ResponseBody>*/
}