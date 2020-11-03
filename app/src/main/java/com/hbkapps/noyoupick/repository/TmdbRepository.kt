package com.hbkapps.noyoupick.repository

import com.hbkapps.noyoupick.Constants
import com.hbkapps.noyoupick.dagger.ActivityScope
import com.hbkapps.noyoupick.model.*
import com.hbkapps.noyoupick.tmdbapi.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

@ActivityScope
class TmdbRepository @Inject constructor(private val tmdbApiInterface: TmdbApiInterface) {

    private var genreList: List<GenreItem> = ArrayList()

    private var mediaListFromSelection: List<Media> = ArrayList()
    private var currentCastList : List<Cast>? = ArrayList()
    private var currentCrewList : List<Crew>? = ArrayList()

    private var mediaType : Int = 0

    fun loadMoviesListFromSelection(callListener: LoadMediaListener, selectedGenres: String) {
        if (mediaListFromSelection.isNullOrEmpty() || mediaListFromSelection.first() !is Movie) {
            makeLoadMoviesListFromSelectionCall(callListener, selectedGenres)
        } else {
            callListener.onMediaListLoaded(mediaListFromSelection)
        }
    }

    private fun makeLoadMoviesListFromSelectionCall(callListener: LoadMediaListener, selectedGenres: String) {
        tmdbApiInterface
                .getMoviesFromUserSelectedGenres(page = 1, selectedGenres = selectedGenres)
                .enqueue(object : Callback<GetMoviesFromSelectionResponse> {
                    override fun onResponse(call: Call<GetMoviesFromSelectionResponse>, response: Response<GetMoviesFromSelectionResponse>) {
                        val responseBody = response.body()
                        if (response.isSuccessful && responseBody != null) {
                            mediaListFromSelection = responseBody.results
                            callListener.onMediaListLoaded(mediaListFromSelection)
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
        if (mediaListFromSelection.isNullOrEmpty() || mediaListFromSelection.first() !is TV) {
            makeLoadTVListFromSelectionCall(callListener, selectedGenres)
        } else {
            callListener.onMediaListLoaded(mediaListFromSelection)
        }
    }

    private fun makeLoadTVListFromSelectionCall(callListener: LoadMediaListener, selectedGenres : String) {
        tmdbApiInterface
                .getTVFromUserSelectedGenres(page = 1, selectedGenres = selectedGenres)
                .enqueue(object : Callback<GetTVFromSelectionResponse> {
                    override fun onResponse(call: Call<GetTVFromSelectionResponse>, response: Response<GetTVFromSelectionResponse>) {
                        Timber.d("tmdb:  %s", response)
                        val responseBody = response.body()
                        if (response.isSuccessful && responseBody != null) {
                            mediaListFromSelection = responseBody.results
                            callListener.onMediaListLoaded(mediaListFromSelection)
                        } else {
                            callListener.onFailure()
                        }
                    }

                    override fun onFailure(call: Call<GetTVFromSelectionResponse>, t: Throwable) {
                        Timber.d(t, "tmdb: %s", t.message)
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

    fun loadCastAndCrewFromMovie(movieId : String, callListener : LoadCastAndCrewListener) {
        tmdbApiInterface.getCreditsFromSelectedMovie(movieId = movieId)
            .enqueue(object : Callback<GetCreditsFromMovieResponse> {
                override fun onResponse(call: Call<GetCreditsFromMovieResponse>, response: Response<GetCreditsFromMovieResponse>) {
                    val responseBody = response.body()
                    Timber.e("in onResponse")
                    if (response.isSuccessful && responseBody != null) {
                        Timber.e("response was successful")
                        currentCastList = responseBody.cast
                        currentCrewList = responseBody.crew
                        callListener.onCastAndCrewLoaded(currentCastList, currentCrewList)
                    } else {
                        callListener.onFailure()
                    }
                }

                override fun onFailure(call: Call<GetCreditsFromMovieResponse>, t: Throwable) {
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

    fun getCrewList(): List<Crew>? {
        return currentCrewList
    }

    fun getCastList(): List<Cast>? {
        return currentCastList
    }

    fun clearMediaListFromSelection() {
        mediaListFromSelection = ArrayList()
    }

    fun getMediaListFromSelection(): List<Media> {
        return mediaListFromSelection
    }

    interface LoadMediaListener {
        fun onMediaListLoaded(mediaList: List<Media>)
        fun onFailure()
    }

    interface LoadCastAndCrewListener {
        fun onCastAndCrewLoaded(castList: List<Cast>?, crewList : List<Crew>?)
        fun onFailure()
    }

    interface GenreListListener {
        fun onGenreListLoaded(genreList: List<GenreItem>)
        fun onFailure()
    }
}