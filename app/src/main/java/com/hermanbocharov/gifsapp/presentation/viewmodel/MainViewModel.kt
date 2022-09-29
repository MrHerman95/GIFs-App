package com.hermanbocharov.gifsapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.hermanbocharov.gifsapp.domain.usecases.GetPagedGifsUseCase
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val getPagedGifsUseCase: GetPagedGifsUseCase
) : ViewModel() {
}