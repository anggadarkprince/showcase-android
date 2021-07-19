package com.anggaari.showcase.models.commons

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class StandardResponse(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: @RawValue Any? = null,
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: String
): Parcelable