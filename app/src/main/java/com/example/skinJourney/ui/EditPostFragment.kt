package com.example.skinJourney.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.skinJourney.R
import com.example.skinJourney.databinding.FragmentEditPostBinding
import com.example.skinJourney.model.Post
import com.squareup.picasso.Picasso

class EditPostFragment : Fragment() {
    private var cameraLauncher: ActivityResultLauncher<Void?>? = null
    private var _binding: FragmentEditPostBinding? = null
    private val binding get() = _binding!!
    private var post: Post? = null

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
        _binding = FragmentEditPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        post?.let {
            binding.postDescription.setText(it.description)
            post?.imageUrl?.let { imageUrl ->
                val url = imageUrl.ifBlank { return }
                Picasso.get()
                    .load(url)
                    .placeholder(R.drawable.profile)
                    .into(binding.postImage)
            }
        }

        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            binding.postImage.setImageBitmap(bitmap)
        }

        binding.cameraButton.setOnClickListener {
            cameraLauncher?.launch(null)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
