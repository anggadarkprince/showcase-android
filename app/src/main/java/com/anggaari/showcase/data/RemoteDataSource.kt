package com.anggaari.showcase.data

import com.anggaari.showcase.data.network.ShowcaseApi
import com.anggaari.showcase.models.award.Result
import com.anggaari.showcase.models.skill.SkillResult
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val showcaseApi: ShowcaseApi) {

    suspend fun getAwards(queries: Map<String, String>): Response<Result> {
        return showcaseApi.getAwards(queries)
    }

    suspend fun getSkills(queries: Map<String, String>): Response<SkillResult> {
        return showcaseApi.getSkills(queries)
    }

}