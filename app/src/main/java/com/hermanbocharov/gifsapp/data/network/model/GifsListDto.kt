package com.hermanbocharov.gifsapp.data.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GifsListDto(
    @SerializedName("data")
    @Expose
    val gifs: List<GifInfoDto>
)