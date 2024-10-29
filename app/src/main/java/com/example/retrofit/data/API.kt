package com.example.retrofit.data

import com.example.retrofit.data.model.Products
import retrofit2.http.GET


interface API {

    @GET("products")
    suspend fun getProductsList() : Products

    companion object{
        const val BASE_URL = "https://dummyjson.com/"
    }
}