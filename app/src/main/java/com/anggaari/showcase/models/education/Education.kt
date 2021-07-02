package com.anggaari.showcase.models.education

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Education(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("degree")
    val degree: String,
    @SerializedName("enter")
    val enter: Int,
    @SerializedName("graduate")
    val graduate: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("institution")
    val institution: String,
    @SerializedName("location")
    val location: String,
    @SerializedName("major")
    val major: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: Int
): Parcelable