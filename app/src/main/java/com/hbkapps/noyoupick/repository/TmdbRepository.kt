package com.hbkapps.noyoupick.repository

import com.hbkapps.noyoupick.dagger.ActivityScope
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

    fun getMoviesList(callListener: MoviesListListener) {
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

    interface MoviesListListener {
        fun loadMoviesList(moviesList: List<Movie>)
        fun onFailure()
    }

}