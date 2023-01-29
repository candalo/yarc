package com.github.candalo.yarc.features.posts.ui

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
    fun getPosts() = repository.fetchPosts("androiddev").cachedIn(viewModelScope)
}