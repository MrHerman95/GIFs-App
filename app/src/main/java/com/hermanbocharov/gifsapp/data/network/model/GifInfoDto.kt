package com.hermanbocharov.gifsapp.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GifInfoDto(
    @SerializedName("id")
    @Expose
    val id: String,

    @SerializedName("title")
    @Expose
    val title: String,

    @SerializedName("images")
    @Expose
    val imageRenditions: ImageRenditionsDto
)