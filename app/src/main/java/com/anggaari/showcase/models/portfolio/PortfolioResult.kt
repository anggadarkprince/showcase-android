package com.anggaari.showcase.models.portfolio

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class PortfolioResult(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<Portfolio>,
    @SerializedName("status")
    val status: String
): Parcelable