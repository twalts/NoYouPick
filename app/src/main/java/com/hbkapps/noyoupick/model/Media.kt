package com.hbkapps.noyoupick.model

abstract class Media {
    abstract fun getMediaTitle(): String?
    abstract fun getMediaOverview(): String?
    abstract fun getMediaPosterPath(): String?
    abstract fun getMediaBackdropPath(): String?
    abstract fun getMediaId() : Long?

    var isExpanded : Boolean = false
}