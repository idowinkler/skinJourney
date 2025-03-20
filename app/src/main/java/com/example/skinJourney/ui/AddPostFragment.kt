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
import com.example.skinJourney.ProgressBarHandler
import com.example.skinJourney.R
import com.example.skinJourney.databinding.FragmentAddPostBinding
import com.example.skinJourney.model.CloudinaryModel
import com.example.skinJourney.model.Post
import com.example.skinJourney.repository.PostRepository
import com.example.skinJourney.utils.analyzeSkinFromBitmap
import com.example.skinJourney.utils.getBitmapIfChanged
import com.example.skinJourney.viewmodel.PostViewModel
import com.example.skinJourney.viewmodel.PostViewModelFactory
import java.util.UUID

class AddPostFragment : Fragment() {
    private var cameraLauncher: ActivityResultLauncher<Void?>? = null
    private var binding: FragmentAddPostBinding? = null
    private lateinit var viewModel: PostViewModel
    private var didPickImage = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val repository = PostRepository()
        val factory = PostViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[PostViewModel::class.java]
        CloudinaryModel.initialize(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddPostBinding.inflate(inflater, container, false)
        binding?.uploadButton?.setOnClickListener(::onUploadClicked)
        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            binding?.postImage?.setImageBitmap(bitmap)
            didPickImage = true
        }
        binding?.cameraButton?.setOnClickListener {
            cameraLauncher?.launch(null)
        }

        val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                binding?.postImage?.setImageURI(it)
            }
        }

        binding?.galleryButton?.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun onUploadClicked(view: View) {
        val description = binding?.postDescription?.text.toString()
        val imageBitmap = getBitmapIfChanged(binding?.postImage, R.drawable.profile, requireContext())

        if (description.isNotEmpty() && imageBitmap != null) {
            ProgressBarHandler.show()

            val userId = com.google.firebase.auth.FirebaseAuth.getInstance().currentUser?.uid ?: "unknown_user"
            val postId = UUID.randomUUID().toString()

            CloudinaryModel.uploadBitmap(imageBitmap, onSuccess = { imageUrl ->
                analyzeSkinFromBitmap("AIzaSyA6KZpdbJZICvqyg4tgXDXV4jQeQML1BxM", imageBitmap) { aiAnalysis ->
                    val post = Post(
                        uid = postId,
                        description = description,
                        imageUrl = imageUrl,
                        aiAnalysis = aiAnalysis,
                        userId = userId
                    )

                    viewModel.addPost(post)
                    Toast.makeText(requireContext(), "Post added", Toast.LENGTH_SHORT).show()
                    ProgressBarHandler.hide()
                    Navigation.findNavController(view).popBackStack()
                }
            }, onError = {
                ProgressBarHandler.hide()
                Toast.makeText(requireContext(), "Image upload failed", Toast.LENGTH_SHORT).show()
            })
        } else {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }

}
