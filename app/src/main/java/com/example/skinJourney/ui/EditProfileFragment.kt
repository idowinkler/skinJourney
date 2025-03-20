package com.example.skinJourney.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.skinJourney.R
import com.example.skinJourney.databinding.FragmentEditProfileBinding
import com.example.skinJourney.viewmodel.UserViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class EditProfileFragment : Fragment() {

    private var binding: FragmentEditProfileBinding? = null
    private var imageUri: Uri? = null
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        // Observe the LiveData for user data
        userViewModel.userLiveData.observe(viewLifecycleOwner, { user ->
            // Update the UI when user data is available
            user?.let {
                binding?.nicknameEditText?.setText(it.nickname)
                // Load the user's profile image, if available
//                it.profileImage?.let { imageUrl ->
//                    Picasso.get().load(imageUrl).into(binding?.profileImageView)
//                }
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

        // Image picker
        val pickImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                imageUri = it
                binding?.profileImageView?.setImageURI(it)
            }
        }

        binding?.changeImageButton?.setOnClickListener {
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
            userViewModel.updateUser(newNickname)
            Navigation.findNavController(it).navigate(R.id.action_editProfileFragment_to_profileFragment)
//            imageUri?.let { uri ->
//                val imageRef = storageRef.child("profile_pics/${user.uid}.jpg")
//                imageRef.putFile(uri).addOnSuccessListener {
//                    imageRef.downloadUrl.addOnSuccessListener { downloadUri ->
//                        database.child("profileImage").setValue(downloadUri.toString())
//                        Toast.makeText(requireContext(), "Profile updated", Toast.LENGTH_SHORT).show()
//                        findNavController().navigateUp()
//                    }
//                }
//            } ?: run {
//                Toast.makeText(requireContext(), "Profile updated", Toast.LENGTH_SHORT).show()
//                findNavController().navigateUp()
//            }
        }

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}