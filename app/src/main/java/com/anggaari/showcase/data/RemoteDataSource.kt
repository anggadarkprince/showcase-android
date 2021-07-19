package com.anggaari.showcase.data

import com.anggaari.showcase.data.network.ShowcaseApi
import com.anggaari.showcase.models.auth.login.LoginResult
import com.anggaari.showcase.models.award.AwardResult
import com.anggaari.showcase.models.commons.StandardResponse
import com.anggaari.showcase.models.education.EducationResult
import com.anggaari.showcase.models.portfolio.PortfolioResult
import com.anggaari.showcase.models.skill.SkillResult
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val showcaseApi: ShowcaseApi) {

    suspend fun login(email: String, password: String): Response<LoginResult> {
        return showcaseApi.login(email, password)
    }

    suspend fun forgotPassword(email: String): Response<StandardResponse> {
        return showcaseApi.forgotPassword(email)
    }

    suspend fun getAwards(queries: Map<String, String>): Response<AwardResult> {
        return showcaseApi.getAwards(queries)
    }

    suspend fun getSkills(queries: Map<String, String>): Response<SkillResult> {
        return showcaseApi.getSkills(queries)
    }

    suspend fun getEducations(queries: Map<String, String>): Response<EducationResult> {
        return showcaseApi.getEducations(queries)
    }

    suspend fun getPortfolios(queries: Map<String, String>): Response<PortfolioResult> {
        return showcaseApi.getPortfolios(queries)
    }

}