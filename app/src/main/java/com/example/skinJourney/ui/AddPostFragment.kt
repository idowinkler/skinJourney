package com.example.skinJourney.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.skinJourney.R
import com.example.skinJourney.databinding.FragmentAddPostBinding
import com.example.skinJourney.model.Model
import com.example.skinJourney.model.Post
import com.example.skinJourney.utils.getBitmapIfChanged

class AddPostFragment : Fragment() {
    private var cameraLauncher: ActivityResultLauncher<Void?>? = null
    private var binding: FragmentAddPostBinding? = null
    private var didPickImage = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPostBinding.inflate(inflater, container, false)
        binding?.uploadButton?.setOnClickListener(::onUploadClicked)

        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            binding?.postImage?.setImageBitmap(bitmap)
            didPickImage = true
        }

        binding?.cameraButton?.setOnClickListener {
            cameraLauncher?.launch(null)
        }

        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    private fun onUploadClicked(view: View) {
        val description = binding?.postDescription?.text.toString()
        val imageBitmap = getBitmapIfChanged(binding?.postImage, R.drawable.profile, requireContext())

        if (description.isNotEmpty() && imageBitmap != null) {
            val post = Post(
                id = binding?.postDescription?.text?.toString() ?: "",
                description = binding?.postDescription?.text?.toString() ?: "",
                imageUrl = "",
                userId = "ido"
            )

            Model.shared.addPost(post, imageBitmap, Model.Storage.CLOUDINARY) {
                Navigation.findNavController(view)?.popBackStack()
            }

        } else {
            Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
        }
    }
}
