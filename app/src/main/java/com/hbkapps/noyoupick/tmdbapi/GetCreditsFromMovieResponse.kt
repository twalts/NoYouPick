package com.hbkapps.noyoupick.tmdbapi

import com.hbkapps.noyoupick.model.Cast
import com.hbkapps.noyoupick.model.Crew
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetCreditsFromMovieResponse(
        @Json(name = "id") val id: Int?,
        @Json(name = "cast") val cast: List<Cast>? = null,
        @Json(name = "crew") val crew: List<Crew>? = null
)