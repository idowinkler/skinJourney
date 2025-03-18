package com.example.skinJourney.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.skinJourney.R
import com.example.skinJourney.databinding.FragmentPostBinding
import com.example.skinJourney.model.Post
import com.squareup.picasso.Picasso

class PostFragment : Fragment() {
    private var _binding: FragmentPostBinding? = null
    private val binding get() = _binding!!
    private var post: Post? = null

//    TODO NOT SHOW EDIT AND DELETE BUTTON WHEN NOT USER'S POST

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

        binding.editButton.setOnClickListener({
            post?.let {
                val action = PostFragmentDirections.actionPostToEditPost(it)
                binding?.root?.let {
                    Navigation.findNavController(it).navigate(action)
                }
            }
        })
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        post?.let {
            binding.postDescription.text = it.description

            post?.imageUrl?.let { imageUrl ->
                val url = imageUrl.ifBlank { return }
                Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.profile)
                    .into(binding.postImage)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
