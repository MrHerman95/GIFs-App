package com.hermanbocharov.gifsapp.data.repository

import androidx.paging.*
import com.hermanbocharov.gifsapp.data.mapper.GifInfoMapper
import com.hermanbocharov.gifsapp.data.GiphyRemoteMediator
import com.hermanbocharov.gifsapp.data.network.database.dao.GifInfoDao
import com.hermanbocharov.gifsapp.domain.entities.GifInfo
import com.hermanbocharov.gifsapp.domain.repository.GifRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GifRepositoryImpl @Inject constructor(
    private val gifInfoDao: GifInfoDao,
    private val remoteMediatorFactory: GiphyRemoteMediator.Factory,
    private val mapper: GifInfoMapper
) : GifRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getPagedGifs(searchKey: String): Flow<PagingData<GifInfo>> {

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = PAGE_SIZE
            ),
            remoteMediator = remoteMediatorFactory.create(searchKey),
            pagingSourceFactory = { gifInfoDao.getPagingSource(searchKey) }
        )
            .flow
            .map { pagingData ->
                pagingData.map { mapper.mapGifInfoEntityToDomain(it) }
            }
    }

    companion object {
        private const val PAGE_SIZE = 25
    }
}