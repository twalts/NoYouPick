package com.hbkapps.noyoupick.dagger

import android.app.Application
import com.hbkapps.noyoupick.NoYouPickApplication
import dagger.Module
import dagger.Provides

@Module(includes = [NetworkModule::class])
class ApplicationModule(var app: NoYouPickApplication) {
    @Provides
    fun provideApplication(): Application {
        return app
    }
}