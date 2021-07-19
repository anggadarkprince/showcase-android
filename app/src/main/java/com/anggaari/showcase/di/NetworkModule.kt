package com.anggaari.showcase.di

import android.content.Context
import android.util.Log
import com.anggaari.showcase.MyApplication
import com.anggaari.showcase.data.DataStoreRepository
import com.anggaari.showcase.data.network.ShowcaseApi
import com.anggaari.showcase.utils.Constants
import com.anggaari.showcase.utils.Constants.Companion.BASE_URL
import com.anggaari.showcase.utils.MyServiceInterceptor
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return MyApplication.getApp()
    }

    @Singleton
    @Provides
    fun provideHttpClient(context: Context, serviceInterceptor: MyServiceInterceptor, dataStoreRepository: DataStoreRepository): OkHttpClient {
        //val accessToken = dataStoreRepository.accessToken.asLiveData()
        //viewModelScope.launch {
        //    val token = accessToken.value
        //}
        val accessToken = Constants.ACCESS_TOKEN
        Log.d("Login2", accessToken)
        return OkHttpClient.Builder()
            .addInterceptor(serviceInterceptor)
            .addInterceptor(ChuckInterceptor(context))
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ShowcaseApi {
        return retrofit.create(ShowcaseApi::class.java)
    }

}