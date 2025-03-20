package com.example.skinJourney.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.skinJourney.R
import com.example.skinJourney.databinding.FragmentEditPostBinding
import com.example.skinJourney.model.CloudinaryModel
import com.example.skinJourney.model.Post
import com.example.skinJourney.repository.PostRepository
import com.example.skinJourney.utils.analyzeSkinFromBitmap
import com.example.skinJourney.utils.getBitmapIfChanged
import com.example.skinJourney.viewmodel.PostViewModel
import com.example.skinJourney.viewmodel.PostViewModelFactory
import com.squareup.picasso.Picasso

class EditPostFragment : Fragment() {
    private var cameraLauncher: ActivityResultLauncher<Void?>? = null
    private var _binding: FragmentEditPostBinding? = null
    private val binding get() = _binding!!
    private var post: Post? = null
    private lateinit var viewModel: PostViewModel
    private var didPickImage = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        CloudinaryModel.initialize(context)
        val repository = PostRepository()
        val factory = PostViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[PostViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        post = arguments?.let {
            EditPostFragmentArgs.fromBundle(it).post
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditPostBinding.inflate(inflater, container, false)
        setupUI()
        return binding.root
    }

    private fun setupUI() {
        post?.let {
            binding.postDescription.setText(it.description)

            if (it.imageUrl.isNotBlank()) {
                Picasso.get()
                    .load(it.imageUrl)
                    .placeholder(R.drawable.profile)
                    .into(binding.postImage)
            }
        }

        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            binding.postImage.setImageBitmap(bitmap)
            didPickImage = true
        }

        binding.cameraButton.setOnClickListener {
            cameraLauncher?.launch(null)
        }

        binding.saveButton.setOnClickListener {
            savePost()
        }
    }

    private fun savePost() {
        val updatedDescription = binding.postDescription.text.toString()
        val imageBitmap = getBitmapIfChanged(binding.postImage, R.drawable.profile, requireContext())

        if (updatedDescription.isNotEmpty() && post != null) {
            // Show loading spinner
            binding.loadingOverlay.visibility = View.VISIBLE
            binding.editPostLayout.animate().alpha(0.5f).setDuration(300).start()

            if (didPickImage && imageBitmap != null) {
                CloudinaryModel.uploadBitmap(imageBitmap, onSuccess = { imageUrl ->
                    analyzeSkinFromBitmap("AIzaSyCvFczlE2yq1hR5z1p-NKicEfdPRkurPKM", imageBitmap) { aiAnalysis ->
                        updatePostInDatabase(updatedDescription, imageUrl, aiAnalysis)
                    }

                    // Hide loading spinner
                    binding.loadingOverlay.visibility = View.GONE
                    binding.editPostLayout.animate().alpha(1f).setDuration(300).start()
                }, onError = {
                    Toast.makeText(requireContext(), "Image upload failed", Toast.LENGTH_SHORT).show()
                })
            } else {
                updatePostInDatabase(updatedDescription, post!!.imageUrl, post!!.aiAnalysis)
                // Hide loading spinner
                binding.loadingOverlay.visibility = View.GONE
                binding.editPostLayout.animate().alpha(1f).setDuration(300).start()
            }
        } else {
            Toast.makeText(requireContext(), "Please enter a description", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updatePostInDatabase(description: String, imageUrl: String, aiAnalysis: String) {
        val updatedPost = post?.copy(
            description = description,
            imageUrl = imageUrl,
            aiAnalysis = aiAnalysis
        )

        updatedPost?.let {
            viewModel.updatePost(it)
            Toast.makeText(requireContext(), "Post updated successfully", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(binding.root).popBackStack()
            Navigation.findNavController(binding.root).popBackStack()

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
