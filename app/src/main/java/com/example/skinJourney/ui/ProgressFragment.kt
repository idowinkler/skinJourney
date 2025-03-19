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
import com.example.skinJourney.ProgressViewModel
import com.example.skinJourney.adapter.OnItemClickListener
import com.example.skinJourney.adapter.ProgressRecyclerAdapter
import com.example.skinJourney.databinding.FragmentProgressBinding
import com.example.skinJourney.model.Model
import com.example.skinJourney.model.Post

class ProgressFragment : Fragment() {

    private var adapter: ProgressRecyclerAdapter? = null
    private var binding: FragmentProgressBinding? = null
    private var viewModel: ProgressViewModel? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this)[ProgressViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProgressBinding.inflate(inflater, container, false)

        binding?.recyclerView?.setHasFixedSize(true)

        val layoutManager = LinearLayoutManager(context)
        binding?.recyclerView?.layoutManager = layoutManager

        adapter = ProgressRecyclerAdapter(viewModel?.posts)

        adapter?.listener = object : OnItemClickListener {
            override fun onItemClick(post: Post?) {
                post?.let {
                    val action = ProgressFragmentDirections.actionProgressToPost(it)
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
