package com.hermanbocharov.gifsapp.domain.usecases

import com.hermanbocharov.gifsapp.domain.repository.GifRepository

class GetPagedGifsUseCase(
    private val repository: GifRepository
) {
    operator fun invoke(searchKey: String) = repository.getPagedGifs(searchKey)
}
