package com.anggaari.showcase.models.auth.login

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginResult(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: LoginData,
    @SerializedName("status")
    val status: String
): Parcelable