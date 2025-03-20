package com.example.skinJourney.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skinJourney.adapter.ExploreRecyclerAdapter
import com.example.skinJourney.adapter.OnItemClickListener
import com.example.skinJourney.databinding.FragmentExploreBinding
import com.example.skinJourney.model.Post
import com.example.skinJourney.model.PostWithUser
import com.example.skinJourney.repository.PostRepository
import com.example.skinJourney.viewmodel.PostViewModel
import com.example.skinJourney.viewmodel.PostViewModelFactory

class ExploreFragment : Fragment() {
    private var adapter: ExploreRecyclerAdapter? = null
    private var binding: FragmentExploreBinding? = null
    private lateinit var viewModel: PostViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val repository = PostRepository()
        val factory = PostViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[PostViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentExploreBinding.inflate(inflater, container, false)
        setupRecyclerView()
        observeViewModel()

        return binding!!.root
    }

    private fun setupRecyclerView() {
        binding?.recyclerView?.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        adapter = ExploreRecyclerAdapter(emptyList()).also { adapterInstance ->
            adapterInstance.listener = object : OnItemClickListener {
                override fun onItemClick(post: PostWithUser?) {
                    post?.let {
                        val postObject = Post(
                            uid = it.id,
                            description = it.description,
                            imageUrl = it.imageUrl,
                            userId = it.userId,
                            aiAnalysis = it.aiAnalysis
                        )
                        val action = ExploreFragmentDirections.actionExploreToPost(postObject)
                        findNavController().navigate(action)
                    }
                }
            }
            binding?.recyclerView?.adapter = adapterInstance
        }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding?.loadingOverlay?.visibility = View.VISIBLE
                binding?.exploreLayout?.animate()?.alpha(0.5f)?.setDuration(300)?.start()
            } else {
                binding?.loadingOverlay?.visibility = View.GONE
                binding?.exploreLayout?.animate()?.alpha(1f)?.setDuration(300)?.start()
            }
        }

        viewModel.otherUsersPosts.observe(viewLifecycleOwner) { posts ->
            adapter?.set(posts)
            adapter?.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchPosts()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
