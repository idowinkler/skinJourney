package com.example.skinJourney.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skinJourney.databinding.ProgressPostListItemFragmentBinding
import com.example.skinJourney.model.PostWithUser

class ProgressRecyclerAdapter(private var posts: List<PostWithUser>?) :
    RecyclerView.Adapter<ProgressViewHolder>() {

    var listener: OnItemClickListener? = null

    fun set(posts: List<PostWithUser>?) {
        this.posts = posts
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = posts?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgressViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ProgressPostListItemFragmentBinding.inflate(inflater, parent, false)
        return ProgressViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: ProgressViewHolder, position: Int) {
        holder.bind(posts?.get(position), position)
    }
}
