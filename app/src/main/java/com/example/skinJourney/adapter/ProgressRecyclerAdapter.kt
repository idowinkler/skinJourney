package com.example.skinJourney.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skinJourney.databinding.ProgressPostListItemFragmentBinding
import com.example.skinJourney.model.Post

class ProgressRecyclerAdapter(private var posts: List<Post>?): RecyclerView.Adapter<ProgressViewHolder>() {
    var listener: OnItemClickListener? = null

    fun set(posts: List<Post>?) {
        this.posts = posts
    }

    override fun getItemCount(): Int = posts?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgressViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        val binding = ProgressPostListItemFragmentBinding.inflate(inflator, parent, false)
        return ProgressViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ProgressViewHolder, position: Int) {
        holder.bind(posts?.get(position), position)
    }
}