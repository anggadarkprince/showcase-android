package com.anggaari.showcase.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.anggaari.showcase.data.Repository
import com.anggaari.showcase.models.award.Result
import com.anggaari.showcase.utils.ConnectionUtil
import com.anggaari.showcase.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AwardViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    var awardsResponse: MutableLiveData<NetworkResult<Result>> = MutableLiveData()

    fun getAwards(queries: Map<String, String>) = viewModelScope.launch {
        getAwardsSafeCall(queries)
    }

    private suspend fun getAwardsSafeCall(queries: Map<String, String>) {
        awardsResponse.value = NetworkResult.Loading()

        if (ConnectionUtil.hasInternetConnection(getApplication<Application>())) {
            try {
                val response = repository.remote.getAwards(queries)
                awardsResponse.value = handleFoodRecipesResponse(response)
            } catch (e: Exception) {
                awardsResponse.value = NetworkResult.Error(e.message)
            }
        } else {
            awardsResponse.value = NetworkResult.Error("No internet connection")
        }
    }

    private fun handleFoodRecipesResponse(response: Response<Result>): NetworkResult<Result> {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 401 -> {
                return NetworkResult.Error("Unauthorized")
            }
            response.body()!!.data.isNullOrEmpty() -> {
                return NetworkResult.Error("Result empty")
            }
            response.isSuccessful -> {
                val awards = response.body()
                return NetworkResult.Success(awards!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

}