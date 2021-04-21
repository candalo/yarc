package github.com.candalo.hashmobilechallenge.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import github.com.candalo.hashmobilechallenge.R
import github.com.candalo.hashmobilechallenge.databinding.ActivityMainBinding

internal class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        inflateFragment()
    }

    private fun inflateFragment() {
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer, PostsFragment())
            .commit()
    }
}