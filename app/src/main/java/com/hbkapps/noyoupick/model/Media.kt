package com.hbkapps.noyoupick.model

import timber.log.Timber

abstract class Media {
    abstract fun getMediaTitle(): String?
    abstract fun getMediaOverview(): String?
    abstract fun getMediaPosterPath(): String?
    abstract fun getMediaBackdropPath(): String?
    abstract fun getMediaId(): Long?

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
}