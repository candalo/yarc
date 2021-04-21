package github.com.candalo.hashmobilechallenge.infrastructure.repository

import androidx.paging.PagingData
import androidx.paging.map
import github.com.candalo.hashmobilechallenge.domain.model.SubRedditPost
import github.com.candalo.hashmobilechallenge.infrastructure.mapper.PostMapper
import github.com.candalo.hashmobilechallenge.infrastructure.model.SubRedditPostResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class PostRepository(
        private val datasource: Flow<PagingData<SubRedditPostResponse>>,
        private val mapper: PostMapper
) {
    fun fetchPosts(): Flow<PagingData<SubRedditPost>> =
            datasource.map { it.map { response -> mapper.map(response) } }
}