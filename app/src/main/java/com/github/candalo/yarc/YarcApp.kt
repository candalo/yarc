package com.github.candalo.yarc

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.candalo.yarc.features.posts.ui.PostsScreen
import com.github.candalo.yarc.theme.YarcTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    YarcTheme {
        Scaffold(
            bottomBar = { YarcBottomNavigation() }
        ) { padding ->
            PostsScreen(Modifier.padding(padding))
        }
    }
}