package com.anggaari.showcase.models.profile


import com.google.gson.annotations.SerializedName

data class Profile(
    @SerializedName("about")
    val about: Any,
    @SerializedName("avatar")
    val avatar: Any,
    @SerializedName("birthday")
    val birthday: Any,
    @SerializedName("contact")
    val contact: Any,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("email_verified_at")
    val emailVerifiedAt: String,
    @SerializedName("gender")
    val gender: Any,
    @SerializedName("id")
    val id: Int,
    @SerializedName("location")
    val location: Any,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("website")
    val website: Any
)