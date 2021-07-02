package com.anggaari.showcase.data.network

import com.anggaari.showcase.models.award.Result
import com.anggaari.showcase.models.education.EducationResult
import com.anggaari.showcase.models.portfolio.PortfolioResult
import com.anggaari.showcase.models.skill.SkillResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface ShowcaseApi {

    @GET("showcase/awards")
    suspend fun getAwards(@QueryMap queries: Map<String, String>): Response<Result>

    @GET("showcase/skills")
    suspend fun getSkills(@QueryMap queries: Map<String, String>): Response<SkillResult>

    @GET("showcase/educations")
    suspend fun getEducations(@QueryMap queries: Map<String, String>): Response<EducationResult>

    @GET("showcase/portfolios")
    suspend fun getPortfolios(@QueryMap queries: Map<String, String>): Response<PortfolioResult>

}