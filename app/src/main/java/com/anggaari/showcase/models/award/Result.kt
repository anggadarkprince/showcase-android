package com.anggaari.showcase.models.award

import com.google.gson.annotations.SerializedName

data class Result(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<Data>,
    @SerializedName("status")
    val status: String
)