package com.github.candalo.yarc.features.posts.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.candalo.yarc.features.posts.domain.model.Post
import com.github.candalo.yarc.features.posts.infrastructure.repository.PostsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
internal class PostsViewModel @Inject constructor(
    private val repository: PostsRepository
) : ViewModel() {
    fun getPosts(subreddit: String): Flow<PagingData<Post>> {
        val subredditSearch = subreddit.ifEmpty {
            "all"
        }
        return repository.fetchPosts(subredditSearch).flowOn(Dispatchers.IO).cachedIn(viewModelScope)
    }
}