package com.udokur.cinetrove.model


import com.google.gson.annotations.SerializedName

data class MovieResponse(

    @SerializedName("results")
    val movieItems: List<MovieItem?>?,
    @SerializedName("page")
    val page: Int?,
    @SerializedName("total_results")
    val totalResults: Int?



    )