package com.hbkapps.noyoupick.tmdbapi

import com.hbkapps.noyoupick.model.Movie
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetMoviesFromSelectionResponse(
        @Json(name = "page") val page: Int,
        @Json(name = "results") val results: List<Movie>,
        @Json(name = "total_pages") val pages: Int
)