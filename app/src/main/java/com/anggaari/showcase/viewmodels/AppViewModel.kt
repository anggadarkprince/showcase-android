package com.anggaari.showcase.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.anggaari.showcase.data.DataStoreRepository
import com.anggaari.showcase.models.user.User
import com.anggaari.showcase.utils.MyServiceInterceptor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    application: Application,
    private val serviceInterceptor: MyServiceInterceptor,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {


    val accessToken = dataStoreRepository.accessToken

    fun saveAccessToken(token: String) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveAccessToken(token)
        }

    fun saveUser(user: User) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveUser(user)
        }

    fun setAccessToken(token: String) {
        serviceInterceptor.setAccessToken(token)
    }

}