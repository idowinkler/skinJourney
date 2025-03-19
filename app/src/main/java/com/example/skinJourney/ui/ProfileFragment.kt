package com.example.skinJourney.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.skinJourney.R
import com.example.skinJourney.databinding.FragmentEditPostBinding
import com.example.skinJourney.databinding.FragmentProfileBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ProfileFragment : Fragment() {
    private var binding: FragmentProfileBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

//        val auth = FirebaseAuth.getInstance()
//        val user = auth.currentUser
//        val database = FirebaseDatabase.getInstance().reference.child("users").child(user!!.uid)
//
//        // Load user data
//        database.get().addOnSuccessListener {
//            val nickname = it.child("nickname").value as? String
//            val profileImage = it.child("profileImage").value as? String
//            binding.nicknameTextView.text = nickname ?: "User"
//            profileImage?.let { url ->
//                Picasso.get().load(url).into(binding.profileImageView)
//            }
//        }

        // Edit profile button
        binding?.editProfileButton?.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_profileFragment_to_editProfileFragment)
        }

//        // Logout button
//        binding?.logoutButton?.setOnClickListener {
//            MaterialAlertDialogBuilder(requireContext())
//                .setTitle("Logout")
//                .setMessage("Are you sure you want to log out?")
//                .setPositiveButton("Yes") { _, _ ->
//                    auth.signOut()
//                    Navigation.findNavController(it)
//                        .navigate(R.id.action_profileFragment_to_loginFragment)
//                }
//                .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
//                .show()
//        }

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
