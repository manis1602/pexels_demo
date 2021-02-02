package com.manikandan.pexels.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.manikandan.pexels.data.Repository
import com.manikandan.pexels.modal.PexelImage
import com.manikandan.pexels.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

//Changes from video
@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    var pexelsResponse : MutableLiveData<NetworkResult<PexelImage>> = MutableLiveData()

    fun getPexelImages(queries: Map<String, String>){
        viewModelScope.launch {
            getPexelImagesSafeCall(queries)
        }
    }

    private suspend fun getPexelImagesSafeCall(queries: Map<String, String>){
        pexelsResponse.value = NetworkResult.Loading()
        if(checkInternetConnection()){
            try {
                val response = repository.remote.getPexelImages(queries)
                pexelsResponse.value = handlePexelsResponse(response)
            }catch (e: Exception){
                Log.d("Exception", e.toString())
                pexelsResponse.value = NetworkResult.Error("No Images Found")
            }
        }else{
            pexelsResponse.value = NetworkResult.Error("No Internet Connection")
        }
    }

    private fun handlePexelsResponse(response: Response<PexelImage>): NetworkResult<PexelImage> {
        return when {
            response.message().toString().contains("timeout") -> {
                NetworkResult.Error("Time Out")
            }
            response.code() == 402 -> {
                NetworkResult.Error("API Limit Reached")
            }
            response.isSuccessful -> {
                val pexelsResponse = response.body()
                NetworkResult.Success(pexelsResponse!!)
            }
            else -> {
                NetworkResult.Error(response.body()!!.totalResults.toString())
            }
        }
    }


    private fun checkInternetConnection(): Boolean {
        val connectivityManager =
            getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?:  return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

}