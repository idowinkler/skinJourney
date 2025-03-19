package com.example.skinJourney.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.skinJourney.model.Post
import com.example.skinJourney.R
import com.example.skinJourney.databinding.ExplorePostListItemFragmentBinding
import com.squareup.picasso.Picasso

class ExploreViewHolder(
    private val binding: ExplorePostListItemFragmentBinding,
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
        // TODO CHANGE WHEN USER INTEGRATION
        binding.username.text = post?.userId

        post?.imageUrl?.let { imageUrl ->
            val url = imageUrl.ifBlank { return }
            // TODO CHANGE WHEN USER INTEGRATION
            Picasso.get()
                .load(url)
                .placeholder(R.drawable.profile)
                .into(binding.userImage)
            Picasso.get()
                .load(url)
                .placeholder(R.drawable.profile)
                .into(binding.postImage)
        }
    }
}