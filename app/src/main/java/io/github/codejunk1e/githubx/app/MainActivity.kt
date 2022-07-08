package io.github.codejunk1e.githubx.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import io.github.codejunk1e.githubx.R
import io.github.codejunk1e.githubx.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUp()
    }


    private fun setUp() {
        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.bottomNavigationView.setupWithNavController(navController)
        binding.toolbar.apply {
            setBackgroundColor(ContextCompat.getColor(this@MainActivity, R.color.white))
            setTitleTextColor(ContextCompat.getColor(this@MainActivity, R.color.black))
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            val title = when (destination.id) {
                R.id.home -> getString(R.string.home)
                R.id.repositories -> getString(R.string.repositories)
                R.id.users, R.id.userprofileFragment -> "Users"
                else -> ""
            }
            binding.toolbar.title = title

            when (destination.id) {
                R.id.home, R.id.repositories, R.id.users -> {
                    binding.toolbar.navigationIcon = null
                }
                else -> {
                    binding.toolbar.apply{
                        navigationIcon = getDrawable(R.drawable.ic_arrow_back)
                    }
                }
            }
        }

        binding.toolbar.setNavigationOnClickListener {
            when (navController.currentDestination?.id) {
                R.id.home, R.id.repositories, R.id.users -> {}
                else -> navController.popBackStack()
            }
        }
    }
}