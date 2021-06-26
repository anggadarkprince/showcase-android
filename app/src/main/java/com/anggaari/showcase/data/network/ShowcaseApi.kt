package com.anggaari.showcase.data.network

import com.anggaari.showcase.models.award.Result
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ShowcaseApi {

    @GET("/showcase/awards")
    suspend fun getRecipes(@QueryMap queries: Map<String, String>): Response<Result>

}