package com.hermanbocharov.gifsapp.data.mapper

import com.hermanbocharov.gifsapp.data.network.database.entities.GifInfoEntity
import com.hermanbocharov.gifsapp.data.network.model.GifInfoDto
import com.hermanbocharov.gifsapp.domain.entities.GifInfo
import javax.inject.Inject

class GifInfoMapper @Inject constructor() {

    fun mapGifInfoEntityToDomain(gifInfoEntity: GifInfoEntity) = GifInfo(
        id = gifInfoEntity.id,
        title = gifInfoEntity.title,
        originalGifUrl = gifInfoEntity.originalGifUrl,
        previewGifUrl = gifInfoEntity.previewGifUrl
    )

    fun mapGifInfoDtoToEntity(gifInfoDto: GifInfoDto, searchKey: String) = GifInfoEntity(
        id = gifInfoDto.id,
        title = gifInfoDto.title,
        originalGifUrl = gifInfoDto.imageRenditions.original.url,
        previewGifUrl = gifInfoDto.imageRenditions.previewGif.url,
        searchKey = searchKey
    )
}
