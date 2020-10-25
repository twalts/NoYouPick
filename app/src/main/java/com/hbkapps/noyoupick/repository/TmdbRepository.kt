package com.hbkapps.noyoupick.repository

import com.hbkapps.noyoupick.Constants
import com.hbkapps.noyoupick.dagger.ActivityScope
import com.hbkapps.noyoupick.model.GenreItem
import com.hbkapps.noyoupick.model.Movie
import com.hbkapps.noyoupick.model.TV
import com.hbkapps.noyoupick.tmdbapi.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@ActivityScope
class TmdbRepository @Inject constructor(private val tmdbApiInterface: TmdbApiInterface) {

    private var genreList: List<GenreItem> = ArrayList()

    private var movieListFromSelection: List<Movie> = ArrayList()
    private var tvListFromSelection: List<TV> = ArrayList()
    private var mediaType : Int = 0

    fun loadMoviesListFromSelection(callListener: LoadMediaListener, selectedGenres: String) {
        if (movieListFromSelection.isNullOrEmpty()) {
            makeLoadMoviesListFromSelectionCall(callListener, selectedGenres)
        } else {
            callListener.onMovieListLoaded(movieListFromSelection)
        }
    }

    private fun makeLoadMoviesListFromSelectionCall(callListener: LoadMediaListener, selectedGenres: String) {
        tmdbApiInterface
                .getMoviesFromUserSelectedGenres(page = 1, selectedGenres = selectedGenres)
                .enqueue(object : Callback<GetMoviesFromSelectionResponse> {
                    override fun onResponse(call: Call<GetMoviesFromSelectionResponse>, response: Response<GetMoviesFromSelectionResponse>) {
                        val responseBody = response.body()
                        if (response.isSuccessful && responseBody != null) {
                            movieListFromSelection = responseBody.movies
                            callListener.onMovieListLoaded(movieListFromSelection)
                        } else {
                            callListener.onFailure()
                        }
                    }

                    override fun onFailure(call: Call<GetMoviesFromSelectionResponse>, t: Throwable) {
                        callListener.onFailure()
                    }
                })
    }

    fun loadTVListFromSelection(callListener: LoadMediaListener, selectedGenres: String) {
        if (tvListFromSelection.isNullOrEmpty()) {
            makeLoadTVListFromSelectionCall(callListener, selectedGenres)
        } else {
            callListener.onTvListLoaded(tvListFromSelection)
        }
    }

    private fun makeLoadTVListFromSelectionCall(callListener: LoadMediaListener, selectedGenres : String) {
        tmdbApiInterface
                .getTVFromUserSelectedGenres(page = 1, selectedGenres = selectedGenres)
                .enqueue(object : Callback<GetTVFromSelectionResponse> {
                    override fun onResponse(call: Call<GetTVFromSelectionResponse>, response: Response<GetTVFromSelectionResponse>) {
                        val responseBody = response.body()
                        if (response.isSuccessful && responseBody != null) {
                            tvListFromSelection = responseBody.movies
                            callListener.onTvListLoaded(tvListFromSelection)
                        } else {
                            callListener.onFailure()
                        }
                    }

                    override fun onFailure(call: Call<GetTVFromSelectionResponse>, t: Throwable) {
                        callListener.onFailure()
                    }
                })
    }

    fun loadGenreList(genreListListener: GenreListListener) {
        if (genreList.isNullOrEmpty()) {
            if (mediaType == Constants.MEDIA_TYPE_MOVIE) {
                makeLoadMovieGenreListCall(genreListListener)
            } else {
                makeLoadTVGenreListCall(genreListListener)
            }
        } else {
            genreListListener.onGenreListLoaded(genreList)
        }
    }

    private fun makeLoadMovieGenreListCall(callListener: GenreListListener) {
        tmdbApiInterface
                .getListOfMovieGenres()
                .enqueue(object : Callback<GetGenresResponse> {
                    override fun onResponse(call: Call<GetGenresResponse>, response: Response<GetGenresResponse>) {
                        val responseBody = response.body()
                        if (response.isSuccessful && responseBody != null) {
                            genreList = responseBody.genres
                            callListener.onGenreListLoaded(genreList)
                        } else {
                            callListener.onFailure()
                        }
                    }

                    override fun onFailure(call: Call<GetGenresResponse>, t: Throwable) {
                        callListener.onFailure()
                    }
                })
    }

    private fun makeLoadTVGenreListCall(callListener: GenreListListener) {
        tmdbApiInterface
                .getListOfTVGenres()
                .enqueue(object : Callback<GetGenresResponse> {
                    override fun onResponse(call: Call<GetGenresResponse>, response: Response<GetGenresResponse>) {
                        val responseBody = response.body()
                        if (response.isSuccessful && responseBody != null) {
                            genreList = responseBody.genres
                            callListener.onGenreListLoaded(genreList)
                        } else {
                            callListener.onFailure()
                        }
                    }

                    override fun onFailure(call: Call<GetGenresResponse>, t: Throwable) {
                        callListener.onFailure()
                    }
                })
    }

    fun getGenreList(): List<GenreItem> {
        return genreList
    }

    fun clearGenreList() {
        (genreList as ArrayList).clear()
    }

    fun setChosenMediaType(mediaTypeSelection: Int) {
        mediaType = mediaTypeSelection
    }

    fun getChosenMediaType(): Int {
        return mediaType
    }

    fun getMoviesListFromSelection(): List<Movie> {
        return movieListFromSelection
    }

    fun getTvListFromSelection(): List<TV> {
        return tvListFromSelection
    }

    fun clearMoviesListFromSelection() {
        movieListFromSelection = ArrayList()
        tvListFromSelection = ArrayList()
    }

    interface LoadMediaListener {
        fun onMovieListLoaded(movieList: List<Movie>)
        fun onTvListLoaded(tvList : List<TV>)
        fun onFailure()
    }

    interface GenreListListener {
        fun onGenreListLoaded(genreList: List<GenreItem>)
        fun onFailure()
    }
}