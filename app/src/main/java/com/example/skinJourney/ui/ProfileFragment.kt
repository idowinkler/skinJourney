package com.example.skinJourney.ui

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.skinJourney.AuthenticationActivity
import com.example.skinJourney.R
import com.example.skinJourney.databinding.FragmentProfileBinding
import com.example.skinJourney.model.FirebaseModel
import com.example.skinJourney.viewmodel.UserViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso

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
            if (user == null) {
                return@observe
            }

            binding?.nicknameTextView?.text = user?.nickname ?: "User"
            val imageUrl = user.imageUrl?.trim()
            if (!imageUrl.isNullOrEmpty()) {
                Picasso.get()
                    .load(imageUrl)
                    .placeholder(R.drawable.profile)
                    .into(binding?.profileImageView)
            }
        }

        binding?.editProfileButton?.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_profileFragment_to_editProfileFragment)
        }

        binding?.logoutButton?.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Logout")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes") { _, _ ->
                    firebaseModel.logout()
                    val intent = Intent(requireContext(), AuthenticationActivity::class.java)
                    startActivity(intent)
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
