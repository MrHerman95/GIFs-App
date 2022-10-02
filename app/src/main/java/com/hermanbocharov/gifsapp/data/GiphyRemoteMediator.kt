package com.hermanbocharov.gifsapp.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.hermanbocharov.gifsapp.data.mapper.GifInfoMapper
import com.hermanbocharov.gifsapp.data.network.api.ApiService
import com.hermanbocharov.gifsapp.data.network.database.dao.GifInfoDao
import com.hermanbocharov.gifsapp.data.network.database.entities.GifInfoEntity
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class GiphyRemoteMediator @AssistedInject constructor(
    private val gifInfoDao: GifInfoDao,
    private val apiService: ApiService,
    private val mapper: GifInfoMapper,
    @Assisted private val searchKey: String
) : RemoteMediator<Int, GifInfoEntity>() {

    private var pageIndex = 0

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, GifInfoEntity>
    ): MediatorResult {

        pageIndex =
            getPageIndex(loadType) ?: return MediatorResult.Success(endOfPaginationReached = true)

        val limit = state.config.pageSize
        val offset = pageIndex * limit

        return try {
            val gifInfoList = getGifsInfo(limit, offset)
            if (loadType == LoadType.REFRESH) {
                gifInfoDao.refresh(searchKey, gifInfoList)
            } else {
                gifInfoDao.insertGifInfoList(gifInfoList)
            }
            MediatorResult.Success(
                endOfPaginationReached = gifInfoList.size < limit
            )
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private fun getPageIndex(loadType: LoadType): Int? {
        pageIndex = when (loadType) {
            LoadType.REFRESH -> 0
            LoadType.PREPEND -> return null
            LoadType.APPEND -> ++pageIndex
        }
        return pageIndex
    }

    private suspend fun getGifsInfo(limit: Int, offset: Int): List<GifInfoEntity> {
        val gifsDto = apiService.getSearchGifsList(
            searchKey = searchKey,
            limit = limit,
            offset = offset
        )

        return gifsDto.gifs.map { mapper.mapGifInfoDtoToEntity(it, searchKey) }
    }

    @AssistedFactory
    interface Factory {
        fun create(searchKey: String): GiphyRemoteMediator
    }
}
