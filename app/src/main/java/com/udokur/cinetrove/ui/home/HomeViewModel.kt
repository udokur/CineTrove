package com.udokur.cinetrove.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udokur.cinetrove.model.MovieItem
import com.udokur.cinetrove.network.ApiClient
import com.udokur.cinetrove.util.Constant
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {



    val movieList: MutableLiveData<List<MovieItem?>?> = MutableLiveData()
    val isLoading = MutableLiveData(false)
    val errorMessage: MutableLiveData<String?> = MutableLiveData()

    private var currentPage = 1 // Şu anki sayfa numarası
    private val pageSize = 20 // Sayfa boyutu

    init {
        getMovieList(currentPage)
    }

    fun loadMoreMovies() {
        currentPage++
        getMovieList(currentPage)
    }

    fun clearMovieList() {
        movieList.value = emptyList()
    }

    fun getMovieList(page: Int) {
        isLoading.value = true

        viewModelScope.launch {
            try {
                val response = ApiClient.getClient().getMovieList(
                    token = Constant.BEARER_TOKEN,
                    page = page,
                    pageSize = pageSize
                )

                if (response.isSuccessful) {
                    val currentList = movieList.value ?: emptyList()
                    val newList = response.body()?.movieItems ?: emptyList()
                    movieList.postValue(currentList + newList)
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