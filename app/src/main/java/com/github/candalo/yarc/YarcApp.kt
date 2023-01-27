package com.github.candalo.yarc

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.github.candalo.yarc.features.posts.ui.PostsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    MaterialTheme {
        PostsScreen()
    }
}