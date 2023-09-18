package com.example.tmdbmovieapp.ui.detail

import android.provider.SyncStateContract
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udokur.cinetrove.model.MovieDetailResponse
import com.udokur.cinetrove.network.ApiClient
import kotlinx.coroutines.launch
import java.lang.Exception
import com.udokur.cinetrove.util.Constant


class DetailViewModel : ViewModel() {

    val movieResponse: MutableLiveData<MovieDetailResponse> = MutableLiveData()
    val isLoading = MutableLiveData(false)
    val errorMessage: MutableLiveData<String?> = MutableLiveData()

    fun getMovieDetail(movieId: Int) {
        isLoading.value = true

        viewModelScope.launch {
            try {
                val response = ApiClient.getClient().getMovieDetail(movieId = movieId.toString(),language ="tr-TR", token = Constant.BEARER_TOKEN)

                if (response.isSuccessful) {
                    movieResponse.postValue(response.body())
                } else {
                    if (response.message().isNullOrEmpty()) {
                        errorMessage.value = "An unknown error occured"
                    } else {
                        errorMessage.value = response.message()
                    }
                }
            } catch (e: Exception) {
                errorMessage.value = e.message
            } finally {
                isLoading.value = false
            }
        }
    }
}