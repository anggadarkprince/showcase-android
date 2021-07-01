package com.anggaari.showcase.models.skill


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SkillResult(
    @SerializedName("code")
    val code: Int,
    @SerializedName("data")
    val `data`: List<Skill>,
    @SerializedName("status")
    val status: String
): Parcelable