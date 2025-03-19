package com.example.skinJourney.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.skinJourney.databinding.ProgressPostListItemFragmentBinding
import com.example.skinJourney.model.Post
import com.example.skinJourney.R
import com.squareup.picasso.Picasso

interface OnItemClickListener {
    fun onItemClick(post: Post?)
}

class ProgressViewHolder(
    private val binding: ProgressPostListItemFragmentBinding,
    listener: OnItemClickListener?
): RecyclerView.ViewHolder(binding.root) {
    private var post: Post? = null

    init {
        itemView.setOnClickListener {
            listener?.onItemClick(post)
        }
    }

    fun bind(post: Post?, position: Int) {
        this.post = post
        binding.description.text = post?.description

        post?.imageUrl?.let { imageUrl ->
            val url = imageUrl.ifBlank { return }
            Picasso.get()
                .load(url)
                .placeholder(R.drawable.profile)
                .into(binding.image)
        }
    }
}