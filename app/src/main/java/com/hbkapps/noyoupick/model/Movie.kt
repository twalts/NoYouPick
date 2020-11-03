package com.hbkapps.noyoupick.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Movie(
    @Json(name="id") val id: Long?,
    @Json(name="title") val title: String?,
    @Json(name="overview") val overview: String?,
    @Json(name="poster_path") val posterPath: String?,
    @Json(name="backdrop_path") val backdropPath: String?,
    @Json(name="vote_average") val rating: Float?,
    @Json(name="release_date") val releaseDate: String?
): Media() {

    override fun getMediaTitle(): String? {
        return title
    }

    override fun getMediaOverview(): String? {
        return overview
    }

    override fun getMediaPosterPath(): String? {
        return posterPath
    }

    override fun getMediaBackdropPath(): String? {
        return backdropPath
    }

    override fun getMediaId(): Long? {
        return id
    }

}