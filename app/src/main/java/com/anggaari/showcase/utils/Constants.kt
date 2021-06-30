package com.anggaari.showcase.utils

class Constants {

    companion object {
        // API Urls
        const val BASE_URL = "http://192.168.1.126:8000/api/"
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
    }
}