package com.anggaari.showcase.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.anggaari.showcase.data.Repository
import com.anggaari.showcase.models.commons.StandardResponse
import com.anggaari.showcase.models.user.UserResponse
import com.anggaari.showcase.utils.ConnectionUtil
import com.anggaari.showcase.utils.MyServiceInterceptor
import com.anggaari.showcase.utils.NetworkResponse
import com.anggaari.showcase.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: Repository,
    private val serviceInterceptor: MyServiceInterceptor,
    application: Application
) : AndroidViewModel(application) {

    var registerResponse: MutableLiveData<NetworkResponse<UserResponse>> = MutableLiveData()
    var loginResponse: MutableLiveData<NetworkResult<UserResponse>> = MutableLiveData()
    var forgotPasswordResponse: MutableLiveData<NetworkResult<StandardResponse>> = MutableLiveData()

    fun register(name: String, email: String, password: String, passwordConfirmation: String) = viewModelScope.launch {
        getRegisterSafeCall(name, email, password, passwordConfirmation)
    }

    fun login(email: String, password: String) = viewModelScope.launch {
        getLoginSafeCall(email, password)
    }

    fun forgotPassword(email: String) = viewModelScope.launch {
        getForgotPasswordSafeCall(email)
    }

    private suspend fun getRegisterSafeCall(name: String, email: String, password: String, passwordConfirmation: String) {
        registerResponse.value = NetworkResponse.Loading()

        if (ConnectionUtil.hasInternetConnection(getApplication<Application>())) {
            try {
                val response = repository.remote.register(name, email, password, passwordConfirmation)
                registerResponse.value = handleRegisterResponse(response)
            } catch (e: Exception) {
                registerResponse.value = NetworkResponse.Error(e.message)
            }
        } else {
            registerResponse.value = NetworkResponse.Error("No internet connection")
        }
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

    private fun handleRegisterResponse(response: Response<UserResponse>): NetworkResponse<UserResponse> {
        return when {
            response.isSuccessful -> {
                val register = response.body()!!
                serviceInterceptor.setAccessToken(register.data.accessToken)
                NetworkResponse.Success(register)
            }
            response.code() == 422 -> {
                Log.e("Validation Error: body", response.body().toString())
                Log.e("Validation Error: errorBody", response.errorBody().toString())
                Log.e("Validation Error: message", response.message())
                Log.e("Validation Error: raw", response.raw().toString())
                NetworkResponse.Error(response.message(), response.code(), response.errorBody())

                //val validationType = object : TypeToken<ValidationErrorResponse<UserValidation>>() {}.type
                //val userValidation = Gson().fromJson<ValidationErrorResponse<UserValidation>>(
                //    response.errorBody()!!.charStream(), validationType
                //)
                //NetworkResult.Error(response.message(), response.code(), errorBody)
            }
            else -> {
                NetworkResponse.Error(response.message(), response.code(), response.errorBody())
            }
        }
    }

    private fun handleDataResponse(response: Response<UserResponse>): NetworkResult<UserResponse> {
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