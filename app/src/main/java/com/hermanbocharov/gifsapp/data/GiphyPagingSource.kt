package com.hermanbocharov.gifsapp.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.hermanbocharov.gifsapp.data.network.api.ApiService
import com.hermanbocharov.gifsapp.domain.entities.GifInfo

class GiphyPagingSource(
    private val apiService: ApiService,
    private val mapper: GifInfoMapper,
    private val searchKey: String
) : PagingSource<Int, GifInfo>() {

    override fun getRefreshKey(state: PagingState<Int, GifInfo>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GifInfo> {
        val pageIndex: Int = params.key ?: 0
        val pageSize: Int = params.loadSize

        return try {
            val gifsDto = apiService.getSearchGifsList(
                searchKey = searchKey,
                limit = pageSize,
                offset = pageIndex
            )

            val gifs = gifsDto.gifs.map { mapper.mapGifInfoDtoToDomain(it) }
            val nextKey = if (gifs.size < pageSize) null else pageIndex + 1
            val prevKey = if (pageIndex == 0) null else pageIndex - 1

            return LoadResult.Page(gifs, prevKey, nextKey)
        } catch (e: Exception) {
            LoadResult.Error(throwable = e)
        }
    }
}
