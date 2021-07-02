package com.anggaari.showcase.models.education

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class EducationResult(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<Education>,
    @SerializedName("status")
    val status: String
): Parcelable