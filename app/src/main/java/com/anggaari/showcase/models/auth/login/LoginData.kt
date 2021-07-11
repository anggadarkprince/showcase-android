package com.anggaari.showcase.models.auth.login

import android.os.Parcelable
import com.anggaari.showcase.models.auth.User
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginData(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("token_type")
    val tokenType: String,
    @SerializedName("user")
    val user: User
): Parcelable