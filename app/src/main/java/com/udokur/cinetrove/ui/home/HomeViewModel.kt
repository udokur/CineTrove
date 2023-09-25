package com.udokur.cinetrove.ui.home

import androidx.lifecycle.LiveData
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

    private var currentPage = 1
    private val pageSize = 20

    private val _searchResult = MutableLiveData<List<MovieItem>>()


    init {
        getMovieList(currentPage)
    }

    fun searchMovies(query: String) {
        isLoading.value = true

        viewModelScope.launch {
            try {
                val response = ApiClient.getClient2().searchMovies(
                    token = Constant.BEARER_TOKEN,
                    query = query
                )

                if (response.isSuccessful) {
                    val results = response.body()?.results
                    val movieItems = results?.map { result ->
                        MovieItem(
                            id = result?.id ?: 0,
                            posterPath = result?.posterPath,
                            title = result?.title,
                            voteAverage = null

                        )
                    } ?: emptyList()

                    movieList.postValue(movieItems)
                } else {
                    if (response.message().isNullOrEmpty()) {
                        errorMessage.value = "Bilinmeyen bir hata"
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
                        errorMessage.value = "Bilinmeyen bir hata "
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
