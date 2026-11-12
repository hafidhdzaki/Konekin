package com.example.konekin.model

import com.google.gson.annotations.SerializedName

data class SingleUserResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("data")
    val data: Data?,
    @SerializedName("message")
    val message: String? = null
)