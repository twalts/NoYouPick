package com.hbkapps.noyoupick.model

import com.hbkapps.noyoupick.Constants

abstract class Media {
    abstract fun getMediaTitle(): String?
    abstract fun getMediaOverview(): String?
    abstract fun getMediaPosterPath(): String?
    abstract fun getMediaBackdropPath(): String?
    abstract fun getMediaId(): Long?
    abstract fun getMediaUserRating(): Float?
    abstract fun getMediaType() : Int

    abstract var crewList: List<Crew>?
    abstract var castList: List<Cast>?
    abstract var creatorList: List<Crew>?

    fun getMediaCrewHeader() : String? {
        return when {
            getMediaType() == Constants.MEDIA_TYPE_MOVIE -> "DIRECTOR"
            getMediaType() == Constants.MEDIA_TYPE_TV -> "CREATOR"
            else -> ""
        }
    }

    fun getMediaDirectorsOrCreators(): String? {
        return when {
            crewList == null -> null
            crewList!!.isEmpty() -> ""
            getMediaType() == Constants.MEDIA_TYPE_MOVIE -> parseDirectors(crewList)
            getMediaType() == Constants.MEDIA_TYPE_TV -> parseCreators(creatorList)
            else -> "ERROR"
        }
    }

    private fun parseCreators(creatorList : List<Crew>?) : String {
        return if (creatorList != null) {
            val cast = StringBuilder()
            var x = 0
            while (x < creatorList.size) {
                creatorList[x].name?.let { cast.append("$it\n") }
                x++
                if (x == 5) break
            }
            cast.toString()
        } else ""
    }

    private fun parseDirectors(crewList: List<Crew>?): String {
        return if (crewList != null && getMediaType() == Constants.MEDIA_TYPE_MOVIE) {
            val directors = StringBuilder()
            for (crew in crewList) {
                if (crew.job == "Director") {
                    crew.name?.let { directors.append("$it\n") }
                }
            }
            directors.toString()
        } else if (crewList != null && getMediaType() == Constants.MEDIA_TYPE_TV) {
            val creators = StringBuilder()
            for (crew in crewList) {
                if (crew.job != null && crew.job.contains("Creator")) {
                    crew.name?.let { creators.append("$it\n") }
                }
            }
            creators.toString()
        } else ""
    }

    fun getMediaCast(): List<Cast>? {
        return when (castList) {
            null -> null
            else -> return castList
        }
    }
}