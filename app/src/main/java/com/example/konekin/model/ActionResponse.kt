package com.example.konekin.model

import com.google.gson.annotations.SerializedName

data class ActionResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("message")
    val message: String? = null
)