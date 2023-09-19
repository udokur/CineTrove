package com.udokur.cinetrove.network

import com.udokur.cinetrove.model.MovieDetailResponse
import com.udokur.cinetrove.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("popular")
    suspend fun getMovieList(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): Response<MovieResponse>


    @GET("{movieId}")
    suspend fun getMovieDetail(
        @Path("movieId") movieId: String,
        @Header("Authorization") token: String,
        @Query("language") language: String
    ): Response<MovieDetailResponse>

    @GET("search")
    suspend fun searchMovies(
        @Header("Authorization") token: String,
        @Query("query") query: String
    ): Response<MovieResponse>

}