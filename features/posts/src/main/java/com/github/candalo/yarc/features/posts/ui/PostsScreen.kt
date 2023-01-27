package com.github.candalo.yarc.features.posts.ui

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun PostsScreen() {
    PostsScreenContent()
}

@Composable
internal fun PostsScreenContent(viewModel: PostsViewModel = hiltViewModel()) {

}