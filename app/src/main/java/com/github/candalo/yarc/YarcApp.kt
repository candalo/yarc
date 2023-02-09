package com.github.candalo.yarc

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.github.candalo.yarc.navigation.YarcAppNavHost
import com.github.candalo.yarc.theme.YarcTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun App() {
    YarcTheme {
        Scaffold(topBar = { YarcTopBar() } ) { padding ->
            YarcAppNavHost(modifier = Modifier.padding(padding).consumedWindowInsets(padding))
        }
    }
}