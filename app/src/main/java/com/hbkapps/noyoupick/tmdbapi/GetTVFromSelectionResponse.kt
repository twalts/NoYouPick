package com.hbkapps.noyoupick.tmdbapi

import com.hbkapps.noyoupick.model.TV
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetTVFromSelectionResponse(
        @Json(name = "page") val page: Int,
        @Json(name = "results") val movies: List<TV>,
        @Json(name = "total_pages") val pages: Int
)