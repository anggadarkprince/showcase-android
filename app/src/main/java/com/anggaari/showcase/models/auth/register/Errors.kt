package com.anggaari.showcase.models.auth.register

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Errors(
    @SerializedName("name")
    val name: List<String>,
    @SerializedName("email")
    val email: List<String>,
    @SerializedName("password")
    val password: List<String>,
    @SerializedName("password_confirmation")
    val passwordConfirmation: List<String>
): Parcelable