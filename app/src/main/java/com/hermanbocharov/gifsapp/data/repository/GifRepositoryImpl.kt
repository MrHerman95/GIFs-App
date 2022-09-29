package com.hermanbocharov.gifsapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hermanbocharov.gifsapp.data.GifInfoMapper
import com.hermanbocharov.gifsapp.data.GiphyPagingSource
import com.hermanbocharov.gifsapp.data.network.api.ApiService
import com.hermanbocharov.gifsapp.domain.entities.GifInfo
import com.hermanbocharov.gifsapp.domain.repository.GifRepository
import kotlinx.coroutines.flow.Flow

class GifRepositoryImpl(
    private val apiService: ApiService,
    private val mapper: GifInfoMapper
) : GifRepository {

    override fun getPagedGifs(searchKey: String): Flow<PagingData<GifInfo>> {

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = PAGE_SIZE
            ),
            pagingSourceFactory = { GiphyPagingSource(apiService, mapper, searchKey) }
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 25
    }
}