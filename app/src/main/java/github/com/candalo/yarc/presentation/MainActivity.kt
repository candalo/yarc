package github.com.candalo.yarc.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import github.com.candalo.yarc.R
import github.com.candalo.yarc.databinding.ActivityMainBinding

internal class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val binding: ActivityMainBinding by viewBinding(R.id.root)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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