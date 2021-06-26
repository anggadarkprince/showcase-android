package com.anggaari.showcase.models.award


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("awarded_in")
    val awardedIn: Int,
    @SerializedName("category")
    val category: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("location")
    val location: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: Int
)