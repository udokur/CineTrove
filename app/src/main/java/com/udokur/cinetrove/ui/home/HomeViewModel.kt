package com.udokur.cinetrove.ui.home

import android.media.session.MediaSession.Token
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udokur.cinetrove.model.MovieItem
import com.udokur.cinetrove.network.ApiClient
import com.udokur.cinetrove.util.Constant
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {


    val movieList : MutableLiveData<List<MovieItem?>?> = MutableLiveData()
    val isLoading = MutableLiveData(false)
    val errorMessage : MutableLiveData<String?> = MutableLiveData()

    fun getMovieList(){
        isLoading.value = true

        viewModelScope.launch {
            try {
                val response = ApiClient.getClient().getMovieList( token = Constant.BEARER_TOKEN)

                if (response.isSuccessful){
                    movieList.postValue(response.body()?.movieItems)
                }else{
                    if (response.message().isNullOrEmpty()){
                        errorMessage.value = "Bilinmeyen Bir hata meydana geldi"
                    }else{
                        errorMessage.value = response.message()
                    }
                }

            } catch (e:Exception){
                errorMessage.value = e.message


            }
            finally {
                isLoading.value = false

            }
        }



    }



}