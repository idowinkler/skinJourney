package com.example.skinJourney.ui

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.skinJourney.databinding.FragmentAddPostBinding
import com.example.skinJourney.model.Post

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
        val post = Post(
            id = binding?.postDescription?.text?.toString() ?: "",
            description = binding?.postDescription?.text?.toString() ?: "",
            imageUrl = "",
            userId = "ido"
        )

        if (didPickImage) {
            binding?.postImage?.isDrawingCacheEnabled = true
            binding?.postImage?.buildDrawingCache()
            val bitmap = (binding?.postImage?.drawable as BitmapDrawable).bitmap
//            Model.shared.add(post, bitmap, Model.Storage.CLOUDINARY) {
//                Navigation.findNavController(view).popBackStack()
//            }
        }
    }
}
