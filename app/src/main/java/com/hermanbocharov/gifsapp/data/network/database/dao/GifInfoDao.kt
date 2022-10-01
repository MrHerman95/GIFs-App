package com.hermanbocharov.gifsapp.data.network.database.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.hermanbocharov.gifsapp.data.network.database.entities.GifInfoEntity

@Dao
interface GifInfoDao {

    @Query("SELECT * FROM gif_info WHERE search_key=:searchKey")
    fun getPagingSource(searchKey: String): PagingSource<Int, GifInfoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGifInfoList(gifsInfo: List<GifInfoEntity>)

    @Query("DELETE FROM gif_info WHERE :searchKey IS NULL OR search_key=:searchKey")
    suspend fun clear(searchKey: String?)

    @Transaction
    suspend fun refresh(searchKey: String?, gifsInfo: List<GifInfoEntity>) {
        clear(searchKey)
        insertGifInfoList(gifsInfo)
    }
}
