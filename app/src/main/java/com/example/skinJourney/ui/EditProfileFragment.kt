package com.example.skinJourney.ui

import android.os.Bundle
import android.util.Log
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
import com.example.skinJourney.databinding.FragmentEditProfileBinding
import com.example.skinJourney.model.CloudinaryModel
import com.example.skinJourney.utils.getBitmapIfChanged
import com.example.skinJourney.viewmodel.UserViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class EditProfileFragment : Fragment() {

    private var binding: FragmentEditProfileBinding? = null
    private lateinit var userViewModel: UserViewModel
    private var cameraLauncher: ActivityResultLauncher<Void?>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userViewModel.userLiveData.observe(viewLifecycleOwner, { user ->
            user?.let {
                binding?.nicknameEditText?.setText(it.nickname)
                val imageUrl = it.imageUrl?.trim() // ✅ Trim spaces to prevent empty values
                if (!imageUrl.isNullOrEmpty()) { // ✅ Prevent Picasso crash
                    Picasso.get().load(imageUrl).into(binding?.profileImageView)
                }
            }
        })

        userViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding?.loadingOverlay?.visibility = View.VISIBLE
                binding?.editProfileLayout?.animate()?.alpha(0.5f)?.setDuration(300)?.start()
            } else {
                binding?.loadingOverlay?.visibility = View.GONE
                binding?.editProfileLayout?.animate()?.alpha(1f)?.setDuration(300)?.start()
            }
        }

        userViewModel.fetchUser()

        CloudinaryModel.initialize(requireContext())

        val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                binding?.profileImageView?.setImageURI(it)
            }
        }

        cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
            binding?.profileImageView?.setImageBitmap(bitmap)
        }

        binding?.cameraButton?.setOnClickListener {
            cameraLauncher?.launch(null)
        }

        binding?.galleryButton?.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        binding?.backButton?.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_editProfileFragment_to_profileFragment)
        }

        // Save button
        binding?.saveButton?.setOnClickListener {
            val newNickname = binding?.nicknameEditText?.text.toString().trim()
            if (newNickname.isEmpty()) {
                view?.let { it1 -> Snackbar.make(it1, "Nickname cannot be empty", Snackbar.LENGTH_SHORT).show() }
                return@setOnClickListener
            }

            val imageBitmap = getBitmapIfChanged(binding?.profileImageView, R.drawable.profile, requireContext())

            if (imageBitmap !== null) {

                CloudinaryModel.uploadBitmap(imageBitmap, onSuccess = { imageUrl ->
                    userViewModel.updateUser(newNickname, imageUrl)
                    Navigation.findNavController(it).navigate(R.id.action_editProfileFragment_to_profileFragment)
                }, onError = {
                    Toast.makeText(requireContext(), "Image upload failed", Toast.LENGTH_SHORT).show()
                })
            } else {
                userViewModel.updateUser(newNickname, null)
                Navigation.findNavController(it).navigate(R.id.action_editProfileFragment_to_profileFragment)
            }

        }

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}