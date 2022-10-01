package com.hermanbocharov.gifsapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hermanbocharov.gifsapp.R
import com.hermanbocharov.gifsapp.presentation.fragments.GifPreviewFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val fragment = GifPreviewFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container_view, fragment)
                .commit()
        }
    }
}
