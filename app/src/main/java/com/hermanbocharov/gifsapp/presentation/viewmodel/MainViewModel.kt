package com.hermanbocharov.gifsapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hermanbocharov.gifsapp.domain.entities.GifInfo
import com.hermanbocharov.gifsapp.domain.usecases.GetPagedGifsUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModel @Inject constructor(
    private val getPagedGifsUseCase: GetPagedGifsUseCase
) : ViewModel() {

    val gifsFlow: Flow<PagingData<GifInfo>>

    private val searchKey = MutableLiveData<String>()

    init {
        gifsFlow = searchKey.asFlow()
            .flatMapLatest { getPagedGifsUseCase(it) }
            .cachedIn(viewModelScope)
    }

    fun setSearchKey(value: String) {
        val valueLowerCaseTrimmed = value.lowercase().trim()
        if (searchKey.value == valueLowerCaseTrimmed) return
        searchKey.postValue(valueLowerCaseTrimmed)
    }
}
