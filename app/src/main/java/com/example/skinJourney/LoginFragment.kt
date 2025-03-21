package com.example.skinJourney

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.skinJourney.databinding.FragmentLoginBinding
import com.example.skinJourney.model.FirebaseModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null
    private lateinit var firebaseModel: FirebaseModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseModel = FirebaseModel()

        // Handle login button click
        binding?.loginButton?.setOnClickListener {
            val email = binding?.emailInput?.text.toString().trim()
            val password = binding?.passwordInput?.text.toString().trim()

            // Check if all fields are filled
            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Show loading spinner
                binding?.loadingOverlay?.visibility = View.VISIBLE
                binding?.loginLayout?.animate()?.alpha(0.5f)?.setDuration(300)?.start()

                // Call the register function
                firebaseModel.login(email, password) { success, error ->
                    if(isAdded) {
                        // Hide loading spinner
                        binding?.loadingOverlay?.visibility = View.GONE
                        binding?.loginLayout?.animate()?.alpha(1f)?.setDuration(300)?.start()

                        if (success) {
                            val intent = Intent(requireContext(), MainActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish()
                        } else {
                            MaterialAlertDialogBuilder(requireContext())
                                .setTitle("Login failed")
                                .setMessage(error)
                                .setNeutralButton("Try again") { dialog, _ ->
                                    dialog.dismiss()
                                }
                                .show()
                        }
                    }
                }
            } else {
                // Show a message if any field is empty
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle navigation to RegisterFragment
        binding?.registerHereTextView?.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
