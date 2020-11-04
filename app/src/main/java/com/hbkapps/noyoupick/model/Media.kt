package com.hbkapps.noyoupick.model

import timber.log.Timber

abstract class Media {
    abstract fun getMediaTitle(): String?
    abstract fun getMediaOverview(): String?
    abstract fun getMediaPosterPath(): String?
    abstract fun getMediaBackdropPath(): String?
    abstract fun getMediaId(): Long?
    abstract fun getMediaUserRating(): Float?

    abstract var crewList: List<Crew>?
    abstract var castList: List<Cast>?


    fun getMediaDirectors(): String? {
        return when {
            crewList == null -> null
            crewList!!.isEmpty() -> ""
            else -> parseDirectors(crewList)
        }
    }

    private fun parseDirectors(crewList: List<Crew>?): String {
        return if (crewList != null) {
            val directors = StringBuilder()
            for (crew in crewList) {
                if (crew.job == "Director") {
                    crew.name?.let { directors.append("$it\n") }
                }
            }
            Timber.e("$directors")
            directors.toString()
        } else ""
    }

    fun getMediaCast(): String? {
        return when {
            castList == null -> null
            castList!!.isEmpty() -> ""
            else -> parseCast(castList)
        }
    }

    private fun parseCast(castList : List<Cast>?) : String {
        return if (castList != null) {
            val cast = StringBuilder()
            var x = 0
            while (x < 5) {
                castList[x].name?.let { cast.append("$it\n") }
                x++
            }
            Timber.e("$cast")
            cast.toString()
        } else ""
    }
}