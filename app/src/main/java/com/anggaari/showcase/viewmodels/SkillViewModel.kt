package com.anggaari.showcase.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.anggaari.showcase.data.Repository
import com.anggaari.showcase.models.skill.SkillResult
import com.anggaari.showcase.utils.ConnectionUtil
import com.anggaari.showcase.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SkillViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    var skillsResponse: MutableLiveData<NetworkResult<SkillResult>> = MutableLiveData()

    fun getSkills(queries: Map<String, String>) = viewModelScope.launch {
        getAwardsSafeCall(queries)
    }

    private suspend fun getAwardsSafeCall(queries: Map<String, String>) {
        skillsResponse.value = NetworkResult.Loading()

        if (ConnectionUtil.hasInternetConnection(getApplication<Application>())) {
            try {
                val response = repository.remote.getSkills(queries)
                skillsResponse.value = handleResponse(response)
            } catch (e: Exception) {
                skillsResponse.value = NetworkResult.Error(e.message)
            }
        } else {
            skillsResponse.value = NetworkResult.Error("No internet connection")
        }
    }

    private fun handleResponse(response: Response<SkillResult>): NetworkResult<SkillResult> {
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
                val skills = response.body()
                return NetworkResult.Success(skills!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

}