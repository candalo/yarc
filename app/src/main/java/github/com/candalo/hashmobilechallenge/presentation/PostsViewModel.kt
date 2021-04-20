package github.com.candalo.hashmobilechallenge.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import github.com.candalo.hashmobilechallenge.infrastructure.repository.PostRepository

internal class PostsViewModel(private val repository: PostRepository) : ViewModel() {
    fun fetchPosts() = repository.fetchPosts().cachedIn(viewModelScope)
}