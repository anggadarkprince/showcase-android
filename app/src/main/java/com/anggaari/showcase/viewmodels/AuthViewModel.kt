package com.anggaari.showcase.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.anggaari.showcase.data.Repository
import com.anggaari.showcase.models.auth.login.LoginResult
import com.anggaari.showcase.models.award.AwardResult
import com.anggaari.showcase.models.commons.StandardResponse
import com.anggaari.showcase.utils.ConnectionUtil
import com.anggaari.showcase.utils.MyServiceInterceptor
import com.anggaari.showcase.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: Repository,
    private val serviceInterceptor: MyServiceInterceptor,
    application: Application
) : AndroidViewModel(application) {

    var loginResponse: MutableLiveData<NetworkResult<LoginResult>> = MutableLiveData()
    var forgotPasswordResponse: MutableLiveData<NetworkResult<StandardResponse>> = MutableLiveData()

    fun login(email: String, password: String) = viewModelScope.launch {
        getLoginSafeCall(email, password)
    }

    fun forgotPassword(email: String) = viewModelScope.launch {
        getForgotPasswordSafeCall(email)
    }

    private suspend fun getLoginSafeCall(email: String, password: String) {
        loginResponse.value = NetworkResult.Loading()

        if (ConnectionUtil.hasInternetConnection(getApplication<Application>())) {
            try {
                val response = repository.remote.login(email, password)
                loginResponse.value = handleDataResponse(response)
            } catch (e: Exception) {
                loginResponse.value = NetworkResult.Error(e.message)
            }
        } else {
            loginResponse.value = NetworkResult.Error("No internet connection")
        }
    }

    private fun handleDataResponse(response: Response<LoginResult>): NetworkResult<LoginResult> {
        return when {
            response.message().toString().contains("timeout") -> {
                NetworkResult.Error("Timeout")
            }
            response.code() == 401 -> {
                NetworkResult.Error("User or password mismatched", response.code())
            }
            response.isSuccessful -> {
                Log.d("Showcase Success", response.code().toString())
                val login = response.body()!!
                serviceInterceptor.setAccessToken(login.data.accessToken)
                NetworkResult.Success(login)
            }
            else -> {
                Log.d("Showcase2", response.code().toString())
                NetworkResult.Error(response.message(), response.code())
            }
        }
    }

    private suspend fun getForgotPasswordSafeCall(email: String) {
        forgotPasswordResponse.value = NetworkResult.Loading()

        if (ConnectionUtil.hasInternetConnection(getApplication<Application>())) {
            try {
                val response = repository.remote.forgotPassword(email)
                forgotPasswordResponse.value = handleForgotPasswordResponse(response)
            } catch (e: Exception) {
                forgotPasswordResponse.value = NetworkResult.Error(e.message)
            }
        } else {
            forgotPasswordResponse.value = NetworkResult.Error("No internet connection")
        }
    }

    private fun handleForgotPasswordResponse(response: Response<StandardResponse>): NetworkResult<StandardResponse> {
        return when {
            response.message().toString().contains("timeout") -> {
                NetworkResult.Error("Timeout")
            }
            response.isSuccessful -> {
                NetworkResult.Success(response.body()!!)
            }
            else -> {
                NetworkResult.Error(response.message(), response.code())
            }
        }
    }
}