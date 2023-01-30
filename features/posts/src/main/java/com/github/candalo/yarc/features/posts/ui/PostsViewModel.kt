package com.github.candalo.yarc.features.posts.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.github.candalo.yarc.features.posts.infrastructure.repository.PostsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class PostsViewModel @Inject constructor(
    private val repository: PostsRepository
) : ViewModel() {
    val subreddit: MutableState<String> = mutableStateOf("")

    fun updateSubreddit(subreddit: String) {
        this.subreddit.value = subreddit
    }

    fun getPosts(subreddit: String) = repository.fetchPosts(subreddit).cachedIn(viewModelScope)
}