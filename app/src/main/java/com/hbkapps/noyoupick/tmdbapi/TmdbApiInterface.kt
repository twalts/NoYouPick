package com.hbkapps.noyoupick.tmdbapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApiInterface {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "053f362926aa9b29f950d9d50890e5aa",
        @Query("page") page: Int
    ): Call<GetPopularMoviesResponse>

    @GET("discover/movie")
    fun getMoviesFromUserSelectedGenres(
            @Query("api_key") apiKey: String = "053f362926aa9b29f950d9d50890e5aa",
            @Query("page") page: Int,
            @Query("with_genres") selectedGenres: String
    ): Call<GetMoviesFromSelectionResponse>

    @GET("discover/tv")
    fun getTVFromUserSelectedGenres(
            @Query("api_key") apiKey: String = "053f362926aa9b29f950d9d50890e5aa",
            @Query("page") page: Int,
            @Query("with_genres") selectedGenres: String
    ): Call<GetTVFromSelectionResponse>

    @GET("genre/movie/list")
    fun getListOfMovieGenres(
            @Query("api_key") apiKey: String = "053f362926aa9b29f950d9d50890e5aa"
    ): Call<GetGenresResponse>

    @GET("genre/tv/list")
    fun getListOfTVGenres(
            @Query("api_key") apiKey: String = "053f362926aa9b29f950d9d50890e5aa"
    ): Call<GetGenresResponse>
}