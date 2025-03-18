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
import com.example.skinJourney.databinding.FragmentRegisterBinding
import com.example.skinJourney.model.FirebaseModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding? = null
    private lateinit var firebaseModel: FirebaseModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseModel = FirebaseModel()

        // Handle register button click
        binding.registerButton.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()
            val nickname = binding.nicknameInput.text.toString().trim()

            // Check if all fields are filled
            if (email.isNotEmpty() && password.isNotEmpty() && nickname.isNotEmpty()) {
                // Call the register function
                firebaseModel.register(email, password, nickname) { success, error ->
                    if(isAdded){
                        if (success) {
                            Log.d("REGISTER", "Registration successful")
                            val intent = Intent(requireContext(), MainActivity::class.java)
                            startActivity(intent)
                            requireActivity().finish()
                        } else {
                            Log.e("REGISTER", "Registration failed: $error")
                            MaterialAlertDialogBuilder(requireContext())
                                .setTitle("Registration failed")
                                .setMessage(error)
                                .setNeutralButton("Try again") { dialog, _ ->
                                    dialog.dismiss()
                                }
                                .show()                        }
                    }
                }
            } else {
                // Show a message if any field is empty
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        // Handle navigation back to LoginFragment
        binding.loginHereTextView.setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_loginFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
