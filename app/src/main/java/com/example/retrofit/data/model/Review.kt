package com.example.retrofit.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Review(
    @Json(name = "comment")
    val comment: String,
    @Json(name = "date")
    val date: String,
    @Json(name = "rating")
    val rating: Int,
    @Json(name = "reviewerEmail")
    val reviewerEmail: String,
    @Json(name = "reviewerName")
    val reviewerName: String
)