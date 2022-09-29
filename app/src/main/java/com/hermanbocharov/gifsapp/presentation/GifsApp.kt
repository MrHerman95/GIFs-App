package com.hermanbocharov.gifsapp.presentation

import android.app.Application
import com.hermanbocharov.gifsapp.di.DaggerApplicationComponent

class GifsApp : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }
}
