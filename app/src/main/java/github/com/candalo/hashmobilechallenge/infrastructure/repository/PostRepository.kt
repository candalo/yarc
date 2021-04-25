package github.com.candalo.hashmobilechallenge.infrastructure.repository

import androidx.paging.PagingData
import androidx.paging.map
import github.com.candalo.hashmobilechallenge.domain.model.Post
import github.com.candalo.hashmobilechallenge.infrastructure.mapper.PostMapper
import github.com.candalo.hashmobilechallenge.infrastructure.model.PostResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class PostRepository(
    private val datasource: Flow<PagingData<PostResponse>>,
    private val mapper: PostMapper
) {
    fun fetchPosts(): Flow<PagingData<Post>> =
        datasource.map { it.map { response -> mapper.map(response) } }
}