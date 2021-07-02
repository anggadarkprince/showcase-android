package com.anggaari.showcase.models.award

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AwardResult(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<Award>,
    @SerializedName("status")
    val status: String
): Parcelable