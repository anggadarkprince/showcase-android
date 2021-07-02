package com.anggaari.showcase.models.portfolio

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Portfolio(
    @SerializedName("comments")
    val comments: Int,
    @SerializedName("company")
    val company: String,
    @SerializedName("cover")
    val cover: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("date")
    val date: String,
    @SerializedName("deleted_at")
    val deletedAt: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("layout")
    val layout: String,
    @SerializedName("order")
    val order: Int,
    @SerializedName("privacy")
    val privacy: String,
    @SerializedName("slug")
    val slug: String,
    @SerializedName("tagline")
    val tagline: String,
    @SerializedName("team")
    val team: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("updated_at")
    val updatedAt: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("views")
    val views: Int
): Parcelable