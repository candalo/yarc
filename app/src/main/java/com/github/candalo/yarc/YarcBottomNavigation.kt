package com.github.candalo.yarc

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun YarcBottomNavigation(modifier: Modifier = Modifier) {
    NavigationBar(modifier = modifier) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Forum,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.nav_bar_posts))
            },
            selected = true,
            onClick = {}
        )
    }
}