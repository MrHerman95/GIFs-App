package com.hermanbocharov.gifsapp.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ImageDto(
    @SerializedName("height")
    @Expose
    val height: String,

    @SerializedName("width")
    @Expose
    val width: String,

    @SerializedName("size")
    @Expose
    val size: String,

    @SerializedName("url")
    @Expose
    val url: String?
)