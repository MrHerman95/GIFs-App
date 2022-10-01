package com.hermanbocharov.gifsapp.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hermanbocharov.gifsapp.databinding.ItemGifPreviewBinding
import com.hermanbocharov.gifsapp.domain.entities.GifInfo

class GifPreviewAdapter :
    PagingDataAdapter<GifInfo, GifPreviewAdapter.GifPreviewViewHolder>(GifInfoDiffCallback()) {

    var onGifPreviewClickListener: ((Int) -> Unit)? = null

    override fun onBindViewHolder(holder: GifPreviewViewHolder, position: Int) {
        val gifInfo = getItem(position) ?: return
        loadGifPreview(holder.binding.ivGifPreview, gifInfo.previewGifUrl)
        holder.binding.tvGifTitle.text = gifInfo.title

        holder.itemView.setOnClickListener {
            Log.d("Presentation", "position = $position")
            onGifPreviewClickListener?.invoke(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GifPreviewViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemGifPreviewBinding.inflate(inflater, parent, false)
        return GifPreviewViewHolder(binding)
    }

    private fun loadGifPreview(iv: ImageView, url: String) {
        val context = iv.context
        Glide.with(context)
            .asGif()
            .load(url)
            .into(iv)
    }

    class GifPreviewViewHolder(
        val binding: ItemGifPreviewBinding
    ) : RecyclerView.ViewHolder(binding.root)
}