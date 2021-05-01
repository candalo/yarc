package github.com.candalo.hashmobilechallenge.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import github.com.candalo.hashmobilechallenge.infrastructure.repository.PostRepository

internal class PostsViewModel(repository: PostRepository) : ViewModel() {
    val posts = repository.fetchPosts().cachedIn(viewModelScope).asLiveData()
}