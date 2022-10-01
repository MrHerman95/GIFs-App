package com.hermanbocharov.gifsapp.di

import android.app.Application
import com.hermanbocharov.gifsapp.data.network.api.ApiFactory
import com.hermanbocharov.gifsapp.data.network.api.ApiService
import com.hermanbocharov.gifsapp.data.network.database.AppDatabase
import com.hermanbocharov.gifsapp.data.network.database.dao.GifInfoDao
import com.hermanbocharov.gifsapp.data.repository.GifRepositoryImpl
import com.hermanbocharov.gifsapp.domain.repository.GifRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    @ApplicationScope
    fun bindGifRepository(impl: GifRepositoryImpl): GifRepository

    companion object {

        @Provides
        @ApplicationScope
        fun provideApiService(): ApiService {
            return ApiFactory.apiService
        }

        @Provides
        @ApplicationScope
        fun provideGifInfoDao(application: Application): GifInfoDao {
            return AppDatabase.getInstance(application).gifInfoDao()
        }
    }
}
