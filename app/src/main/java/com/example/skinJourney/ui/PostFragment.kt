package com.example.skinJourney.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.skinJourney.R
import com.example.skinJourney.databinding.FragmentPostBinding
import com.example.skinJourney.model.FirebaseModel
import com.example.skinJourney.model.Post
import com.example.skinJourney.repository.PostRepository
import com.example.skinJourney.viewmodel.PostViewModel
import com.example.skinJourney.viewmodel.PostViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso

class PostFragment : Fragment() {
    private var _binding: FragmentPostBinding? = null
    private val binding get() = _binding!!
    private var post: Post? = null
    private lateinit var viewModel: PostViewModel
    private lateinit var firebaseModel: FirebaseModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val repository = PostRepository()
        val factory = PostViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[PostViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        post = arguments?.let {
            PostFragmentArgs.fromBundle(it).post
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostBinding.inflate(inflater, container, false)
        setupUI()

        return binding.root
    }

    private fun setupUI() {
        post?.let {
            binding.postDescription.text = it.description
            binding.aiAnalysis.text = it.aiAnalysis

            it.imageUrl.let { imageUrl ->
                if (imageUrl.isNotBlank()) {
                    Picasso.get()
                        .load(imageUrl)
                        .placeholder(R.drawable.profile)
                        .into(binding.postImage)
                }
            }
        }

        firebaseModel = FirebaseModel()
        val currentUserId = firebaseModel.getCurrentUser()
        if (post?.userId != currentUserId) {
            binding.editButton.visibility = View.GONE
            binding.deleteButton.visibility = View.GONE
        } else {
            binding.editButton.setOnClickListener {
                post?.let {
                    val action = PostFragmentDirections.actionPostToEditPost(it)
                    Navigation.findNavController(binding.root).navigate(action)
                }
            }

            binding.deleteButton.setOnClickListener {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Delete")
                    .setMessage("Are you sure you want to delete this post?")
                    .setPositiveButton("Yes") { _, _ ->
                        deletePost()
                    }
                    .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
                    .show()
            }
        }
    }

    private fun deletePost() {
        post?.let {
            viewModel.deletePost(it)
            Toast.makeText(requireContext(), "Post deleted", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(binding.root).popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
