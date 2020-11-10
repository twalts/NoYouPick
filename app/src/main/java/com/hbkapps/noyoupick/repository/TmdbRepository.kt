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

    fun loadCastAndCrewFromMovie(media: Media, callListener: LoadCastAndCrewListener) {
        tmdbApiInterface.getCreditsFromSelectedMovie(movieId = media.getMediaId().toString())
            .enqueue(object : Callback<GetCreditsFromMediaResponse> {
                override fun onResponse(call: Call<GetCreditsFromMediaResponse>, response: Response<GetCreditsFromMediaResponse>) {
                    val responseBody = response.body()
                    if (response.isSuccessful && responseBody != null) {
                        callListener.onCastAndCrewLoaded(media, responseBody.cast, responseBody.crew)
                    } else {
                        callListener.onFailure(media)
                    }
                }

                override fun onFailure(call: Call<GetCreditsFromMediaResponse>, t: Throwable) {
                    callListener.onFailure(media)
                }
            })
    }

    fun loadCastAndCrewFromTvShow(media: Media, callListener: LoadCastAndCrewListener) {
        tmdbApiInterface.getCreditsFromSelectedTvShow(tvId = media.getMediaId().toString())
                .enqueue(object : Callback<GetCreditsFromMediaResponse> {
                    override fun onResponse(call: Call<GetCreditsFromMediaResponse>, response: Response<GetCreditsFromMediaResponse>) {
                        val responseBody = response.body()
                        if (response.isSuccessful && responseBody != null) {
                            callListener.onCastAndCrewLoaded(media, responseBody.cast, responseBody.crew)
                        } else {
                            callListener.onFailure(media)
                        }
                    }

                    override fun onFailure(call: Call<GetCreditsFromMediaResponse>, t: Throwable) {
                        callListener.onFailure(media)
                    }
                })
    }

    fun loadCreatorFromTvShow(media: Media, callListener: LoadCreatorListener) {
        tmdbApiInterface.getCreatorFromSelectedTvShow(tvId = media.getMediaId().toString())
                .enqueue(object : Callback<GetCreatorFromTvShowResponse> {
                    override fun onResponse(call: Call<GetCreatorFromTvShowResponse>, response: Response<GetCreatorFromTvShowResponse>) {
                        val responseBody = response.body()
                        if (response.isSuccessful && responseBody != null) {
                            callListener.onCreatorLoaded(media, responseBody.creator)
                        } else {
                            callListener.onFailure(media)
                        }
                    }

                    override fun onFailure(call: Call<GetCreatorFromTvShowResponse>, t: Throwable) {
                        callListener.onFailure(media)
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
        fun onCastAndCrewLoaded(media: Media, castList: List<Cast>?, crewList : List<Crew>?)
        //fun onCreatorLoaded(media: Media, creatorList : List<Crew>?)
        fun onFailure(media: Media)
    }

    interface LoadCreatorListener {
        fun onCreatorLoaded(media: Media, creatorList : List<Crew>?)
        fun onFailure(media: Media)
    }

    interface GenreListListener {
        fun onGenreListLoaded(genreList: List<GenreItem>)
        fun onFailure()
    }
}