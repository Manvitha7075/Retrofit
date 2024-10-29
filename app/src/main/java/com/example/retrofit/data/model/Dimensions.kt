package com.example.retrofit.data.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Dimensions(
    @Json(name = "depth")
    val depth: Double,
    @Json(name = "height")
    val height: Double,
    @Json(name = "width")
    val width: Double
)