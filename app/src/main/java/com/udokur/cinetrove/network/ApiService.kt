package com.udokur.cinetrove.network

import com.udokur.cinetrove.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {

    @GET("now_playing")
    suspend fun  getMovieList(@Header("Authorization") token :String): Response<MovieResponse>




}