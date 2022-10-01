package com.hermanbocharov.gifsapp.data.network.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "gif_info")
data class GifInfoEntity(
    @PrimaryKey
    val id: String,
    val title: String,
    @ColumnInfo(name = "original_gif_url")
    val originalGifUrl: String,
    @ColumnInfo(name = "preview_gif_url")
    val previewGifUrl: String,
    @ColumnInfo(name = "search_key")
    val searchKey: String
)
