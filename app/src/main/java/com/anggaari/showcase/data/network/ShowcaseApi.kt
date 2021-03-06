package com.anggaari.showcase.data.network

import com.anggaari.showcase.models.user.UserResponse
import com.anggaari.showcase.models.award.AwardResult
import com.anggaari.showcase.models.commons.StandardResponse
import com.anggaari.showcase.models.education.EducationResult
import com.anggaari.showcase.models.portfolio.PortfolioResult
import com.anggaari.showcase.models.skill.SkillResult
import com.anggaari.showcase.utils.Constants
import retrofit2.Response
import retrofit2.http.*

interface ShowcaseApi {
    @FormUrlEncoded
    @POST("register")
    @Headers(Constants.NO_AUTH_HEADER_KEY + ": true")
    suspend fun register(
        @Field("name") name:String,
        @Field("email") email:String,
        @Field("password") password:String,
        @Field("password_confirmation") passwordConfirmation:String,
    ): Response<UserResponse>

    @FormUrlEncoded
    @POST("login")
    @Headers(Constants.NO_AUTH_HEADER_KEY + ": true")
    suspend fun login(
        @Field("email") email:String,
        @Field("password") password:String
    ): Response<UserResponse>

    @FormUrlEncoded
    @POST("forgot-password")
    @Headers(Constants.NO_AUTH_HEADER_KEY + ": true")
    suspend fun forgotPassword(@Field("email") email:String): Response<StandardResponse>

    @GET("showcase/awards")
    suspend fun getAwards(@QueryMap queries: Map<String, String>): Response<AwardResult>

    @GET("showcase/skills")
    suspend fun getSkills(@QueryMap queries: Map<String, String>): Response<SkillResult>

    @GET("showcase/educations")
    suspend fun getEducations(@QueryMap queries: Map<String, String>): Response<EducationResult>

    @GET("showcase/portfolios")
    suspend fun getPortfolios(@QueryMap queries: Map<String, String>): Response<PortfolioResult>

}