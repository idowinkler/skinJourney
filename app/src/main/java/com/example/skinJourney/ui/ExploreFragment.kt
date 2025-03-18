package com.example.skinJourney.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skinJourney.ExploreViewModel
import com.example.skinJourney.ProgressViewModel
import com.example.skinJourney.adapter.ExploreRecyclerAdapter
import com.example.skinJourney.adapter.OnItemClickListener
import com.example.skinJourney.adapter.ProgressRecyclerAdapter
import com.example.skinJourney.databinding.FragmentExploreBinding
import com.example.skinJourney.databinding.FragmentProgressBinding
import com.example.skinJourney.model.Model
import com.example.skinJourney.model.Post

class ExploreFragment : Fragment() {

    private var adapter: ExploreRecyclerAdapter? = null
    private var binding: FragmentExploreBinding? = null
    private var viewModel: ExploreViewModel? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this)[ExploreViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExploreBinding.inflate(inflater, container, false)

        binding?.recyclerView?.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(context)
        binding?.recyclerView?.layoutManager = layoutManager

        adapter = ExploreRecyclerAdapter(viewModel?.posts)

        adapter?.listener = object : OnItemClickListener {
            override fun onItemClick(post: Post?) {
                post?.let {
                    val action = ExploreFragmentDirections.actionExploreToPost(it)
                    binding?.root?.let {
                        Navigation.findNavController(it).navigate(action)
                    }
                }
            }
        }

        binding?.recyclerView?.adapter = adapter

        return binding?.root
    }

    override fun onResume() {
        super.onResume()
        getAllProgressPosts()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun getAllProgressPosts() {
        Model.shared.getAllProgressPosts {
            viewModel?.set(posts = it)
            adapter?.set(it)
            adapter?.notifyDataSetChanged()

        }
    }
}
