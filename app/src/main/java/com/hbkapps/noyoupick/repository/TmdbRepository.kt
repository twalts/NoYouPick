package com.hbkapps.noyoupick.repository

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

    private var popularMoviesList: List<Movie> = ArrayList()
    private var tvGenreList : List<GenreItem> = ArrayList()
    private var movieGenreList : List<GenreItem> = ArrayList()

    private var movieListFromSelection: List<Movie> = ArrayList()
    private var tvListFromSelection: List<TV> = ArrayList()
    private var selectedGenreList : ArrayList<GenreItem> = ArrayList()
    private var mediaType : Int = 0

    fun loadPopularMoviesList(callListener: MoviesListListener) {
        if (popularMoviesList.isNullOrEmpty()) {
            makeLoadPopularMoviesListCall(callListener)
        } else {
            callListener.loadMovieList(popularMoviesList)
        }
    }

    private fun makeLoadPopularMoviesListCall(callListener: MoviesListListener) {
        tmdbApiInterface.getPopularMovies(page = 1)
            .enqueue(object : Callback<GetPopularMoviesResponse> {
                override fun onResponse(call: Call<GetPopularMoviesResponse>, response: Response<GetPopularMoviesResponse>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()

                        if (responseBody != null) {
                            popularMoviesList = responseBody.movies
                            callListener.loadMovieList(popularMoviesList)
                        } else {
                            callListener.onFailure()
                        }
                    }
                }

                override fun onFailure(call: Call<GetPopularMoviesResponse>, t: Throwable) {
                    callListener.onFailure()
                }
            })
    }

    fun loadMoviesListFromSelection(callListener: MoviesListListener, selectedGenres: String) {
        if (movieListFromSelection.isNullOrEmpty()) {
            makeLoadMoviesListFromSelectionCall(callListener, selectedGenres)
        } else {
            callListener.loadMovieList(movieListFromSelection)
        }
    }

    private fun makeLoadMoviesListFromSelectionCall(callListener: MoviesListListener,
                                                    selectedGenres : String) {
        tmdbApiInterface.getMoviesFromUserSelectedGenres(page = 1, selectedGenres = selectedGenres)
                .enqueue(object : Callback<GetMoviesFromSelectionResponse> {
                    override fun onResponse(call: Call<GetMoviesFromSelectionResponse>, response: Response<GetMoviesFromSelectionResponse>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()

                            if (responseBody != null) {
                                movieListFromSelection = responseBody.movies
                                callListener.loadMovieList(movieListFromSelection)
                            } else {
                                callListener.onFailure()
                            }
                        }
                    }

                    override fun onFailure(call: Call<GetMoviesFromSelectionResponse>, t: Throwable) {
                        callListener.onFailure()
                    }
                })
    }

    fun loadTVListFromSelection(callListener: MoviesListListener, selectedGenres: String) {
        if (tvListFromSelection.isNullOrEmpty()) {
            makeLoadTVListFromSelectionCall(callListener, selectedGenres)
        } else {
            callListener.loadTVList(tvListFromSelection)
        }
    }

    private fun makeLoadTVListFromSelectionCall(callListener: MoviesListListener,
                                                    selectedGenres : String) {
        tmdbApiInterface.getTVFromUserSelectedGenres(page = 1, selectedGenres = selectedGenres)
                .enqueue(object : Callback<GetTVFromSelectionResponse> {
                    override fun onResponse(call: Call<GetTVFromSelectionResponse>, response: Response<GetTVFromSelectionResponse>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()

                            if (responseBody != null) {
                                tvListFromSelection = responseBody.movies
                                callListener.loadTVList(tvListFromSelection)
                            } else {
                                callListener.onFailure()
                            }
                        }
                    }

                    override fun onFailure(call: Call<GetTVFromSelectionResponse>, t: Throwable) {
                        callListener.onFailure()
                    }
                })
    }

    fun loadMovieGenreList(callListener: GenreListListener) {
        if (movieGenreList.isNullOrEmpty()) {
            makeLoadMovieGenreListCall(callListener)
        } else {
            callListener.loadMovieGenres(movieGenreList)
        }
    }

    private fun makeLoadMovieGenreListCall(callListener: GenreListListener) {
        tmdbApiInterface.getListOfMovieGenres()
                .enqueue(object : Callback<GetGenresResponse> {
                    override fun onResponse(call: Call<GetGenresResponse>, response: Response<GetGenresResponse>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()

                            if (responseBody != null) {
                                movieGenreList = responseBody.genres
                                callListener.loadMovieGenres(movieGenreList)
                            } else {
                                callListener.onFailure()
                            }
                        }
                    }

                    override fun onFailure(call: Call<GetGenresResponse>, t: Throwable) {
                        callListener.onFailure()
                    }

                })
    }

    fun loadTVGenreList(callListener: GenreListListener) {
        if (tvGenreList.isNullOrEmpty()) {
            makeLoadTVGenreListCall(callListener)
        } else {
            callListener.loadTVGenres(tvGenreList)
        }
    }

    private fun makeLoadTVGenreListCall(callListener: GenreListListener) {
        tmdbApiInterface.getListOfTVGenres()
                .enqueue(object : Callback<GetGenresResponse> {
                    override fun onResponse(call: Call<GetGenresResponse>, response: Response<GetGenresResponse>) {
                        if (response.isSuccessful) {
                            val responseBody = response.body()

                            if (responseBody != null) {
                                tvGenreList = responseBody.genres
                                callListener.loadTVGenres(tvGenreList)
                            } else {
                                callListener.onFailure()
                            }
                        }
                    }

                    override fun onFailure(call: Call<GetGenresResponse>, t: Throwable) {
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

    fun getMovieGenreList() : List<GenreItem> {
        return movieGenreList
    }

    fun getTVGenreList() : List<GenreItem> {
        return tvGenreList
    }

    fun clearMoviesListFromSelection() {
        movieListFromSelection = ArrayList()
        tvListFromSelection = ArrayList()
    }

    interface MoviesListListener {
        fun loadMovieList(movieList: List<Movie>)
        fun loadTVList(tvList : List<TV>)
        fun onFailure()
    }

    interface GenreListListener {
        fun loadMovieGenres(movieGenreList : List<GenreItem>)
        fun loadTVGenres(tvGenreList : List<GenreItem>)
        fun onFailure()
    }
}