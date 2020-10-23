package com.hbkapps.noyoupick.dagger

import com.hbkapps.noyoupick.genreselection.GenreSelectionActivity
import com.hbkapps.noyoupick.landing.LandingActivity
import com.hbkapps.noyoupick.medialist.MediaListActivity
import com.hbkapps.noyoupick.repository.RepositoryModule
import dagger.Component

@ApplicationScope
@Component(modules = [ApplicationModule::class, PresenterModule::class, RepositoryModule::class])
interface ApplicationComponent {
    fun inject(landingActivity: LandingActivity)
    fun inject(genreSelectionActivity: GenreSelectionActivity)
    fun inject(mediaListActivity: MediaListActivity)
}