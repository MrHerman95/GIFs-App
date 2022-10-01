package com.hermanbocharov.gifsapp.di

import android.app.Application
import com.hermanbocharov.gifsapp.presentation.fragments.GifFullscreenFragment
import com.hermanbocharov.gifsapp.presentation.fragments.GifPreviewFragment
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
