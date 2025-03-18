package com.example.skinJourney.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skinJourney.databinding.ExplorePostListItemFragmentBinding
import com.example.skinJourney.model.Post

class ExploreRecyclerAdapter(private var posts: List<Post>?): RecyclerView.Adapter<ExploreViewHolder>() {
    var listener: OnItemClickListener? = null

    fun set(posts: List<Post>?) {
        this.posts = posts
    }

    override fun getItemCount(): Int = posts?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val binding = ExplorePostListItemFragmentBinding.inflate(inflator, parent, false)
        return ExploreViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ExploreViewHolder, position: Int) {
        holder.bind(posts?.get(position), position)
    }
}