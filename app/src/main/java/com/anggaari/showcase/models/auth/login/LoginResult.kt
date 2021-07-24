package com.anggaari.showcase.models.auth.login

import android.os.Parcelable
import com.anggaari.showcase.models.auth.register.Errors
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class LoginResult(
    @SerializedName("code")
    val code: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("data")
    val data: LoginData,
    @SerializedName("errors")
    val errors: Errors?,
    @SerializedName("message")
    val message: String?,
): Parcelable