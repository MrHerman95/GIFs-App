package com.hermanbocharov.gifsapp.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hermanbocharov.gifsapp.databinding.ItemGifOriginalBinding
import com.hermanbocharov.gifsapp.domain.entities.GifInfo

class GifOriginalAdapter :
    PagingDataAdapter<GifInfo, GifOriginalAdapter.GifOriginalViewHolder>(GifInfoDiffCallback()) {

    override fun onBindViewHolder(holder: GifOriginalViewHolder, position: Int) {
        val gifInfo = getItem(position) ?: return
        loadGifOriginal(holder.binding.ivGifOriginal, gifInfo.originalGifUrl)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifOriginalViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGifOriginalBinding.inflate(inflater, parent, false)
        return GifOriginalViewHolder(binding)
    }

    private fun loadGifOriginal(iv: ImageView, url: String) {
        val context = iv.context
        Glide.with(context)
            .asGif()
            .load(url)
            .into(iv)
    }

    class GifOriginalViewHolder(
        val binding: ItemGifOriginalBinding
    ) : RecyclerView.ViewHolder(binding.root)
}
