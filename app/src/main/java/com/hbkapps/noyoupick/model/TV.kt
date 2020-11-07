package com.hbkapps.noyoupick.model

import com.hbkapps.noyoupick.Constants
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

    override var crewList: List<Crew>? = null
    override var castList: List<Cast>? = null
    override var creatorList: List<Crew>? = null

    override fun getMediaTitle(): String? {
        return name
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

    override fun getMediaUserRating(): Float? {
        return rating
    }

    override fun getMediaType(): Int {
        return Constants.MEDIA_TYPE_TV
    }
}