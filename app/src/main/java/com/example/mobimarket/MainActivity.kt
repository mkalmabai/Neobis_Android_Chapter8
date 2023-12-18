package com.example.mobimarket

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.mobimarket.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.mainFragmentsHolder) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNavigationView = binding.bottomMenu
        val addProductButton = binding.addProduct

        val bottomAppBar = binding.bottomAppBar
        bottomNavigationView.background =null
        bottomNavigationView.menu.getItem(2).isEnabled =false
        bottomNavigationView.menu.getItem(1).isEnabled =false
        bottomNavigationView.setupWithNavController(navController)
        binding.addProduct.setOnClickListener {
            navController.navigate(R.id.addProductFragment)
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment,R.id.profileFragment -> {
                    bottomNavigationView.visibility = View.VISIBLE
                    bottomAppBar.visibility = View.VISIBLE
                    addProductButton.visibility = View.VISIBLE
                }
                else -> {
                    bottomNavigationView.visibility = View.GONE
                    bottomAppBar.visibility = View.GONE
                    addProductButton.visibility = View.GONE
                }
            }
        }
    }
}