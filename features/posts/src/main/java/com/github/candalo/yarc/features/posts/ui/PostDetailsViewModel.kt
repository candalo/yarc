package com.github.candalo.yarc.features.posts.ui

import androidx.lifecycle.ViewModel
import com.github.candalo.yarc.features.posts.infrastructure.repository.PostsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
internal class PostDetailsViewModel @Inject constructor(
    private val repository: PostsRepository
) : ViewModel() {
    fun getPostDetails(
        permalink: String
    ) = repository.fetchPostComments(permalink).flowOn(Dispatchers.IO)
}