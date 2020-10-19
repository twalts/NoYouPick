package com.hbkapps.noyoupick

import android.app.Application
import com.hbkapps.noyoupick.dagger.ApplicationComponent
import com.hbkapps.noyoupick.dagger.ApplicationModule
import com.hbkapps.noyoupick.dagger.ContextModule
import com.hbkapps.noyoupick.dagger.DaggerApplicationComponent
import timber.log.Timber
import timber.log.Timber.DebugTree

class NoYouPickApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        Timber.plant(DebugTree())
        applicationComponent = DaggerApplicationComponent.builder()
            .contextModule(ContextModule(this))
            .applicationModule(ApplicationModule(this))
            .build()
    }
}