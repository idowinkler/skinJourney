//package com.example.skinJourney
//
//import android.os.Bundle
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//import androidx.fragment.app.Fragment
//import com.example.skinJourney.databinding.ActivityAuthenticationBinding
//
//
//class AuthenticationActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityAuthenticationBinding  // ViewBinding instance
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//
//        // Inflate the binding for the activity
//        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        // Load LoginFragment initially
//        if (savedInstanceState == null) {
//            loadFragment(LoginFragment())
//        }
//    }
//
//    // Function to load a fragment dynamically
//    private fun loadFragment(fragment: Fragment) {
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.fragmentContainerView, fragment)
//            .addToBackStack(null) // Enables back navigation
//            .commit()
//    }
//
//    // Public methods to switch between fragments
//    fun navigateToRegister() {
//        loadFragment(RegisterFragment())
//    }
//
//    fun navigateToLogin() {
//        loadFragment(LoginFragment())
//    }
//}


package com.example.skinJourney

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import com.example.skinJourney.databinding.ActivityAuthenticationBinding

class AuthenticationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthenticationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.authenticationContainer)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
