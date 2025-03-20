package com.example.skinJourney.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skinJourney.adapter.OnItemClickListener
import com.example.skinJourney.adapter.ProgressRecyclerAdapter
import com.example.skinJourney.databinding.FragmentProgressBinding
import com.example.skinJourney.model.Post
import com.example.skinJourney.model.PostWithUser
import com.example.skinJourney.repository.PostRepository
import com.example.skinJourney.viewmodel.PostViewModel
import com.example.skinJourney.viewmodel.PostViewModelFactory

class ProgressFragment : Fragment() {
    private var _binding: FragmentProgressBinding? = null
    private val binding get() = _binding
    private var viewModel: PostViewModel? = null
    private var adapter: ProgressRecyclerAdapter? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val repository = PostRepository()
        val factory = PostViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[PostViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProgressBinding.inflate(inflater, container, false)
        binding?.let { setupRecyclerView(it) }
        observeViewModel()
        return binding?.root
    }

    private fun setupRecyclerView(binding: FragmentProgressBinding) {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
        }

        adapter = ProgressRecyclerAdapter(emptyList()).also { adapterInstance ->
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
                        val action = ProgressFragmentDirections.actionProgressToPost(postObject)
                        findNavController().navigate(action)
                    }
                }
            }
            binding.recyclerView.adapter = adapterInstance
        }
    }

    private fun observeViewModel() {
        viewModel?.userPosts?.observe(viewLifecycleOwner) { posts ->
            adapter?.set(posts)
            adapter?.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel?.fetchPosts()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
