package com.hermanbocharov.gifsapp.domain.usecases

import com.hermanbocharov.gifsapp.domain.repository.GifRepository
import javax.inject.Inject

class GetPagedGifsUseCase @Inject constructor(
    private val repository: GifRepository
) {
    operator fun invoke(searchKey: String) = repository.getPagedGifs(searchKey)
}
