package com.example.skinJourney.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skinJourney.databinding.ExplorePostListItemFragmentBinding
import com.example.skinJourney.model.PostWithUser

class ExploreRecyclerAdapter(private var posts: List<PostWithUser>?): RecyclerView.Adapter<ExploreViewHolder>() {
    var listener: OnItemClickListener? = null

    fun set(posts: List<PostWithUser>?) {
        this.posts = posts
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = posts?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ExplorePostListItemFragmentBinding.inflate(inflater, parent, false)
        return ExploreViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ExploreViewHolder, position: Int) {
        holder.bind(posts?.get(position), position)
    }
}
