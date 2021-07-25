package com.anggaari.showcase.models.user

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    @SerializedName("about")
    val about: String,
    @SerializedName("avatar")
    val avatar: String,
    @SerializedName("birthday")
    val birthday: String,
    @SerializedName("contact")
    val contact: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("email_verified_at")
    val emailVerifiedAt: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("location")
    val location: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("website")
    val website: String
): Parcelable