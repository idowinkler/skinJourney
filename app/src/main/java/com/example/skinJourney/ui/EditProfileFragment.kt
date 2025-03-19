package com.example.skinJourney.ui

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.skinJourney.R
import com.example.skinJourney.databinding.FragmentEditProfileBinding
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class EditProfileFragment : Fragment() {

    private var binding: FragmentEditProfileBinding? = null
    private var imageUri: Uri? = null
//    private val storageRef = FirebaseStorage.getInstance().reference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)
//        val auth = FirebaseAuth.getInstance()
//        val user = auth.currentUser
//        val database = FirebaseDatabase.getInstance().reference.child("users").child(user!!.uid)

        // Load existing data
//        database.get().addOnSuccessListener {
//            val nickname = it.child("nickname").value as? String
//            val profileImage = it.child("profileImage").value as? String
//            binding?.nicknameEditText?.setText(nickname ?: "")
//            profileImage?.let { url ->
//                Picasso.get().load(url).into(binding?.profileImageView)
//            }
//        }

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

//        // Save button
//        binding?.saveButton?.setOnClickListener {
//            val newNickname = binding?.nicknameEditText?.text.toString().trim()
//            if (newNickname.isEmpty()) {
//                view?.let { it1 -> Snackbar.make(it1, "Nickname cannot be empty", Snackbar.LENGTH_SHORT).show() }
//                return@setOnClickListener
//            }
//            database.child("nickname").setValue(newNickname)
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
//        }

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}