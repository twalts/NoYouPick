package com.hbkapps.noyoupick.tmdbapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface TmdbApiInterface {

    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "053f362926aa9b29f950d9d50890e5aa",
        @Query("page") page: Int
    ): Call<GetMoviesResponse>

}