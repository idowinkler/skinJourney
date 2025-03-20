package com.example.skinJourney.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.skinJourney.databinding.ExplorePostListItemFragmentBinding
import com.example.skinJourney.R
import com.example.skinJourney.model.PostWithUser
import com.squareup.picasso.Picasso

class ExploreViewHolder(
    private val binding: ExplorePostListItemFragmentBinding,
    listener: OnItemClickListener?
): RecyclerView.ViewHolder(binding.root) {

    private var post: PostWithUser? = null

    init {
        itemView.setOnClickListener {
            listener?.onItemClick(post)
        }
    }

    fun bind(post: PostWithUser?, position: Int) {
        this.post = post
        binding.username.text = post?.userName

        post?.imageUrl?.let { userImageUrl ->
            if (userImageUrl.isNotBlank()) {
                Picasso.get()
                    .load(userImageUrl)
                    .placeholder(R.drawable.profile)
                    .into(binding.userImage)
            }
        }

        post?.imageUrl?.let { imageUrl ->
            if (imageUrl.isNotBlank()) {
                Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.profile)
                    .into(binding.postImage)
            }
        }
    }
}
