package com.hermanbocharov.gifsapp.presentation.adapters

import androidx.recyclerview.widget.DiffUtil
import com.hermanbocharov.gifsapp.domain.entities.GifInfo

class GifInfoDiffCallback : DiffUtil.ItemCallback<GifInfo>() {

    override fun areItemsTheSame(oldItem: GifInfo, newItem: GifInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GifInfo, newItem: GifInfo): Boolean {
        return oldItem == newItem
    }
}
