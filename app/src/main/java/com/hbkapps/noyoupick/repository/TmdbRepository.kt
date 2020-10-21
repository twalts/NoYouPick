package com.hbkapps.noyoupick.repository

import com.hbkapps.noyoupick.dagger.ActivityScope
import com.hbkapps.noyoupick.model.GenreItem
import com.hbkapps.noyoupick.model.Movie
import com.hbkapps.noyoupick.tmdbapi.GetMoviesResponse
import com.hbkapps.noyoupick.tmdbapi.TmdbApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@ActivityScope
class TmdbRepository @Inject constructor(private val tmdbApiInterface: TmdbApiInterface) {

    private var moviesList: List<Movie> = ArrayList()
    private var selectedGenreList : ArrayList<GenreItem> = ArrayList()
    private var mediaType : Int = 0

    fun getPopularMoviesList(callListener: MoviesListListener) {
        if (moviesList.isNullOrEmpty()) {
            makeGetMoviesListCall(callListener)
        } else {
            callListener.loadMoviesList(moviesList)
        }
    }

    private fun makeGetMoviesListCall(callListener: MoviesListListener) {
        tmdbApiInterface.getPopularMovies(page = 1)
            .enqueue(object : Callback<GetMoviesResponse> {
                override fun onResponse(call: Call<GetMoviesResponse>, response: Response<GetMoviesResponse>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            moviesList = responseBody.movies
                            callListener.loadMoviesList(moviesList)
                        } else {
                            callListener.onFailure()
                        }
                    }
                }

                override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                    callListener.onFailure()
                }
            })

    }

    fun getMoviesListFromSelection(callListener: MoviesListListener, selectedGenres: String) {
        if (moviesList.isNullOrEmpty()) {
            makeGetMoviesListFromSelectionCall(callListener, selectedGenres)
        } else {
            callListener.loadMoviesList(moviesList)
        }
    }

    private fun makeGetMoviesListFromSelectionCall(callListener: MoviesListListener,
                                                   selectedGenres : String) {
        tmdbApiInterface.getMoviesFromUserSelectedGenres(page = 1, selectedGenres = selectedGenres)
                .enqueue(object : Callback<GetMoviesResponse> {
                    override fun onResponse(call: Call<GetMoviesResponse>, response: Response<GetMoviesResponse>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()

                            if (responseBody != null) {
                                moviesList = responseBody.movies
                                callListener.loadMoviesList(moviesList)
                            } else {
                                callListener.onFailure()
                            }
                        }
                    }

                    override fun onFailure(call: Call<GetMoviesResponse>, t: Throwable) {
                        callListener.onFailure()
                    }
                })

    }

    fun setChosenMediaType(mediaTypeSelection : Int) {
        mediaType = mediaTypeSelection
    }

    fun getChosenMediaType() : Int {
        return mediaType
    }

    fun getSelectedGenresList() : ArrayList<GenreItem> {
        return selectedGenreList
    }

    fun clearMoviesList() {
        moviesList = ArrayList()
    }

    interface MoviesListListener {
        fun loadMoviesList(moviesList: List<Movie>)
        fun onFailure()
    }

}