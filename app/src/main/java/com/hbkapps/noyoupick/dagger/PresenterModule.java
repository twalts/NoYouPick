package com.hbkapps.noyoupick.dagger;

import com.hbkapps.noyoupick.genreselection.GenreSelectionPresenter;
import com.hbkapps.noyoupick.landing.LandingPresenter;
import com.hbkapps.noyoupick.repository.TmdbRepository;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class PresenterModule {

    @Provides
    @ApplicationScope
    public static LandingPresenter getLandingPresenter(TmdbRepository tmdbRepository) {
        return new LandingPresenter(tmdbRepository);
    }

    @Provides
    @ApplicationScope
    public static GenreSelectionPresenter getGenreSelectionPresenter(TmdbRepository tmdbRepository) {
        return new GenreSelectionPresenter(tmdbRepository);
    }

}