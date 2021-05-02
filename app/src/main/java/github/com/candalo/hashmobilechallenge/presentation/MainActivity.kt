package github.com.candalo.hashmobilechallenge.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import github.com.candalo.hashmobilechallenge.R
import github.com.candalo.hashmobilechallenge.databinding.ActivityMainBinding

internal class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureToolbar()
    }

    private fun configureToolbar() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        val appBarConfiguration =
            AppBarConfiguration(navHostFragment.navController.graph)

        with(binding.toolbar) {
            setSupportActionBar(this)
            setupWithNavController(navHostFragment.navController, appBarConfiguration)
        }
    }
}