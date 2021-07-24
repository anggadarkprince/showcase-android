package com.anggaari.showcase.models.auth.register

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ValidationResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("errors")
    val errors: Errors,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
): Parcelable