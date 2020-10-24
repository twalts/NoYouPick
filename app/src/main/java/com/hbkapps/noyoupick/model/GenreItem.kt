package com.hbkapps.noyoupick.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenreItem(
        @Json(name="id") val id: Long,
        @Json(name="name") val genreName: String
)