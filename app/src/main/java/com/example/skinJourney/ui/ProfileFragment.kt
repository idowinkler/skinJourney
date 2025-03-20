package com.example.skinJourney.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.skinJourney.R
import com.example.skinJourney.databinding.FragmentEditPostBinding
import com.example.skinJourney.databinding.FragmentProfileBinding
import com.example.skinJourney.model.FirebaseModel
import com.example.skinJourney.viewmodel.UserViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ProfileFragment : Fragment() {
    private var binding: FragmentProfileBinding? = null
    private lateinit var firebaseModel: FirebaseModel
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        firebaseModel = FirebaseModel()

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        userViewModel.fetchUser()

        userViewModel.userLiveData.observe(viewLifecycleOwner) { user ->
            binding?.nicknameTextView?.text = user?.nickname ?: "User"
        }

        // Edit profile button
        binding?.editProfileButton?.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_profileFragment_to_editProfileFragment)
        }

        // Logout
        binding?.logoutButton?.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Logout")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes") { _, _ ->
                    firebaseModel.logout()
//                    Navigation.findNavController(requireView()).navigate(R.id.action_profileFragment_to_loginFragment)
                }
                .setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
                .show()
        }

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
