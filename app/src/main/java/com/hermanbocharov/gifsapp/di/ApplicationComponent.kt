package com.hermanbocharov.gifsapp.di

import android.app.Application
import com.hermanbocharov.gifsapp.presentation.GifFullscreenFragment
import com.hermanbocharov.gifsapp.presentation.GifPreviewFragment
import com.hermanbocharov.gifsapp.presentation.GifsApp
import com.hermanbocharov.gifsapp.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(fragment: GifPreviewFragment)
    fun inject(fragment: GifFullscreenFragment)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}
