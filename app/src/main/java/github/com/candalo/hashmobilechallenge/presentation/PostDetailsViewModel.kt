package github.com.candalo.hashmobilechallenge.presentation

import androidx.lifecycle.ViewModel
import github.com.candalo.hashmobilechallenge.infrastructure.repository.PostCommentRepository

internal class PostDetailsViewModel(private val repository: PostCommentRepository) : ViewModel() {
    suspend fun fetchComments(postCommentPermalink: String) =
        repository.fetchComments(postCommentPermalink)
}