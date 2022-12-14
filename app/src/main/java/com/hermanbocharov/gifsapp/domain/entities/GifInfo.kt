package com.hermanbocharov.gifsapp.domain.entities

data class GifInfo(
    val id: String,
    val title: String,
    val originalGifUrl: String?,
    val previewGifUrl: String?,
)
