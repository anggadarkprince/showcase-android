package com.anggaari.showcase.utils

import com.anggaari.showcase.BuildConfig

class Constants {

    companion object {
        // API Urls
        const val BASE_URL = BuildConfig.BASE_URL
        const val API_PORTFOLIO_URL = "${BASE_URL}showcase/portfolios"
        const val API_SKILL_URL = "${BASE_URL}showcase/skills"
        const val API_AWARD_URL = "${BASE_URL}showcase/awards"
        const val API_EDUCATION_URL = "${BASE_URL}showcase/educations"

        // ROOM Database
        const val DATABASE_NAME = "showcase_database"
        const val PROFILES_TABLE = "profiles_table"
        const val PORTFOLIOS_TABLE = "portfolios_table"
        const val SKILLS_TABLE = "skills_table"
        const val AWARDS_TABLE = "award_table"
        const val EDUCATIONS_TABLE = "education_table"

        const val PREFERENCES_NAME = "showcase_preferences"
        const val PREFERENCES_ACCESS_TOKEN = "access_token"
        const val PREFERENCES_USER = "user"

        const val NO_AUTH_HEADER_KEY = "No-Authentication"
        var ACCESS_TOKEN = ""
    }
}