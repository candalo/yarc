package github.com.candalo.yarc.infrastructure.repository

import androidx.paging.PagingData
import androidx.paging.map
import github.com.candalo.yarc.domain.model.Post
import github.com.candalo.yarc.infrastructure.mapper.Mapper
import github.com.candalo.yarc.infrastructure.model.PostResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class PostRepository(
    private val datasource: Flow<PagingData<PostResponse>>,
    private val mapper: Mapper<PostResponse, Post>
) {
    fun fetchPosts(): Flow<PagingData<Post>> =
        datasource.map { it.map { response -> mapper.map(response) } }
}