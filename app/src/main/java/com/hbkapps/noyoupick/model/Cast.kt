package com.hbkapps.noyoupick.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Cast(
    @Json(name="cast_id") val castId: Int?,
    @Json(name="character") val character: String?,
    @Json(name="credit_id") val creditId: String?,
    @Json(name="gender") val gender: Int?,
    @Json(name="id") val id: Int?,
    @Json(name="name") val name: String?,
    @Json(name="order") val order: Int?,
    @Json(name="profile_path") val profilePath : String?
)