package com.hermanbocharov.gifsapp.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ImageRenditionsDto(
    @SerializedName("original")
    @Expose
    val original: ImageDto,

    @SerializedName("preview_gif")
    @Expose
    val previewGif: ImageDto
)