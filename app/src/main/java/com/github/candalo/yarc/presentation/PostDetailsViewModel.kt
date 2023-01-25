package com.github.candalo.yarc.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.github.candalo.yarc.infrastructure.repository.PostCommentRepository

internal class PostDetailsViewModel(private val repository: PostCommentRepository) : ViewModel() {
    suspend fun fetchComments(postCommentPermalink: String) =
        repository.fetchComments(postCommentPermalink).asLiveData()
}