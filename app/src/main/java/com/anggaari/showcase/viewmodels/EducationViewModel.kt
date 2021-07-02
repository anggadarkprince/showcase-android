package com.anggaari.showcase.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.anggaari.showcase.data.Repository
import com.anggaari.showcase.models.education.EducationResult
import com.anggaari.showcase.models.skill.SkillResult
import com.anggaari.showcase.utils.ConnectionUtil
import com.anggaari.showcase.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class EducationViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    var educationsResponse: MutableLiveData<NetworkResult<EducationResult>> = MutableLiveData()

    fun getSkills(queries: Map<String, String>) = viewModelScope.launch {
        getDataSafeCall(queries)
    }

    private suspend fun getDataSafeCall(queries: Map<String, String>) {
        educationsResponse.value = NetworkResult.Loading()

        if (ConnectionUtil.hasInternetConnection(getApplication<Application>())) {
            try {
                val response = repository.remote.getEducations(queries)
                educationsResponse.value = handleResponse(response)
            } catch (e: Exception) {
                educationsResponse.value = NetworkResult.Error(e.message)
            }
        } else {
            educationsResponse.value = NetworkResult.Error("No internet connection")
        }
    }

    private fun handleResponse(response: Response<EducationResult>): NetworkResult<EducationResult> {
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
                val educations = response.body()
                return NetworkResult.Success(educations!!)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

}