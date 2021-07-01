package com.anggaari.showcase.models.skill


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Skill(
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("expertise")
    val expertise: String,
    @SerializedName("field")
    val `field`: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("order")
    val order: Int,
    @SerializedName("proficiency_level")
    val proficiencyLevel: Int,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: Int
): Parcelable