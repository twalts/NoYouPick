package com.hbkapps.noyoupick.tmdbapi

import com.hbkapps.noyoupick.model.GenreItem
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetGenresResponse(
        @Json(name = "genres") val genres: List<GenreItem>
)