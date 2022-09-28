package com.hermanbocharov.gifsapp.data

import com.hermanbocharov.gifsapp.data.network.model.GifInfoDto
import com.hermanbocharov.gifsapp.domain.entities.GifInfo

class GifInfoMapper {

    fun mapGifInfoDtoToDomain(gifInfoDto: GifInfoDto) = GifInfo(
        id = gifInfoDto.id,
        originalGifUrl = gifInfoDto.imageRenditions.original.url,
        previewGifUrl = gifInfoDto.imageRenditions.previewGif.url
    )
}