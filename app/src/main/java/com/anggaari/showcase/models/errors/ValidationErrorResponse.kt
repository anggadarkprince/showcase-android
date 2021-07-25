package com.anggaari.showcase.models.errors

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class ValidationErrorResponse<T>(
    val status: String,
    val code: Int,
    val message: String,
    val errors: @RawValue T,
): Parcelable