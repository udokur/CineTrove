package com.udokur.cinetrove.network

import com.udokur.cinetrove.model.MovieResponse
import com.udokur.cinetrove.util.Constant
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header

interface ApiService {

    @GET("popular")
    suspend fun getMovieList(@Header("Authorization") token: String): Response<MovieResponse>


}

