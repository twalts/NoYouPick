package com.hbkapps.noyoupick.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TV(
    @Json(name="id") val id: Long?,
    @Json(name="name") val name: String?,
    @Json(name="overview") val overview: String?,
    @Json(name="poster_path") val posterPath: String?,
    @Json(name="backdrop_path") val backdropPath: String?,
    @Json(name="vote_average") val rating: Float?,
    @Json(name="first_air_date") val firstAirDate: String?
): Media() {

    override fun getMediaTitle(): String? {
        return name
    }
}