package com.hbkapps.noyoupick.tmdbapi

import com.hbkapps.noyoupick.model.Crew
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetCreatorFromTvShowResponse(
        @Json(name = "created_by") val creator: List<Crew>?
)