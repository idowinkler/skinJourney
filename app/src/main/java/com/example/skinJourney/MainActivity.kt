package com.example.skinJourney

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class MainActivity : AppCompatActivity() {
    private var navController: NavController? = null
    private lateinit var fab: ExtendedFloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ProgressBarHandler.init(this)

        val navHostFragment: NavHostFragment? =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
        navController = navHostFragment?.navController

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        navController?.let { NavigationUI.setupWithNavController(bottomNavigationView, it) }

        bottomNavigationView.setOnItemSelectedListener { item ->
            val currentFragmentId = navController?.currentDestination?.id
            if (currentFragmentId != item.itemId) {
                navController?.navigate(item.itemId)
            }
            true
        }

        fab = findViewById(R.id.fab_add_post)

        fab.setOnClickListener {
            navController?.navigate(R.id.action_global_addPostFragment)
        }

        navController?.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.progressFragment || destination.id == R.id.exploreFragment) {
                fab.show()
            } else {
                fab.hide()
            }
        }
    }
}
