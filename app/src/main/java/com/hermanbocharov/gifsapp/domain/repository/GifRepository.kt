package com.hermanbocharov.gifsapp.domain.repository

import androidx.paging.PagingData
import com.hermanbocharov.gifsapp.domain.entities.GifInfo
import kotlinx.coroutines.flow.Flow

interface GifRepository {

    fun getPagedGifs(searchKey: String): Flow<PagingData<GifInfo>>
}
