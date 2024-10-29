package com.example.retrofit.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Meta(
    @Json(name = "barcode")
    val barcode: String,
    @Json(name = "createdAt")
    val createdAt: String,
    @Json(name = "qrCode")
    val qrCode: String,
    @Json(name = "updatedAt")
    val updatedAt: String
)