package com.hbkapps.noyoupick.repository

import dagger.Provides
import com.hbkapps.noyoupick.dagger.ApplicationScope
import com.hbkapps.noyoupick.tmdbapi.TmdbApiInterface
import dagger.Module

@Module
class RepositoryModule {

    @Provides
    @ApplicationScope
    fun provideTmdbRepository(tmdbApiInterface: TmdbApiInterface?): TmdbRepository {
        return TmdbRepository(tmdbApiInterface!!)
    }
}