package com.udokur.cinetrove.model


import com.google.gson.annotations.SerializedName

data class Results(

    @SerializedName("id")
    val id: Int?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("title")
    val title: String?,
    @SerializedName("vote_average")
    val voteAverage: Double?,

)