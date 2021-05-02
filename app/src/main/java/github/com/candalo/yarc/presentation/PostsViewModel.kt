package github.com.candalo.yarc.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import github.com.candalo.yarc.infrastructure.repository.PostRepository

internal class PostsViewModel(repository: PostRepository) : ViewModel() {
    val posts = repository.fetchPosts().cachedIn(viewModelScope).asLiveData()
}