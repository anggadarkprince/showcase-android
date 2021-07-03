package com.anggaari.showcase.models.profile

import com.google.gson.annotations.SerializedName

data class ProfileResult(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: Profile,
    @SerializedName("status")
    val status: String
)