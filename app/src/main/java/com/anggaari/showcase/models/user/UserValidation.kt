package com.anggaari.showcase.models.user

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserValidation(
    @SerializedName("name")
    val name: List<String>?,
    @SerializedName("email")
    val email: List<String>?,
    @SerializedName("password")
    val password: List<String>?,
    @SerializedName("password_confirmation")
    val passwordConfirmation: List<String>?
): Parcelable