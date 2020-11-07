package com.hbkapps.noyoupick.model

import com.hbkapps.noyoupick.Constants
import timber.log.Timber

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

    fun getMediaCreators() : String? {
        return when {
            creatorList == null -> null
            creatorList!!.isEmpty() -> ""
            else -> parseCreators(creatorList)
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
            Timber.e("$cast")
            cast.toString()
        } else ""
    }

    fun getMediaDirectors(): String? {
        return when {
            crewList == null -> null
            crewList!!.isEmpty() -> ""
            else -> parseDirectors(crewList)
        }
    }

    private fun parseDirectors(crewList: List<Crew>?): String {
        return if (crewList != null && getMediaType() == Constants.MEDIA_TYPE_MOVIE) {
            val directors = StringBuilder()
            for (crew in crewList) {
                if (crew.job == "Director") {
                    crew.name?.let { directors.append("$it\n") }
                }
            }
            Timber.e("$directors")
            directors.toString()
        } else if (crewList != null && getMediaType() == Constants.MEDIA_TYPE_TV) {
            val creators = StringBuilder()
            for (crew in crewList) {
                if (crew.job!!.contains("Creator")) {
                    crew.name?.let { creators.append("$it\n") }
                }
            }
            Timber.e("$creators")
            creators.toString()
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
            while (x < castList.size) {
                castList[x].name?.let { cast.append("$it\n") }
                x++
                if (x == 5) break
            }
            Timber.e("$cast")
            cast.toString()
        } else ""
    }
}