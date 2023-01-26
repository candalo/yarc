package com.github.candalo.yarc.features.posts.infrastructure.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.github.candalo.yarc.converter.Mapper
import com.github.candalo.yarc.features.posts.domain.model.Post
import com.github.candalo.yarc.features.posts.infrastructure.datasource.PostsPagingSourceFactory
import com.github.candalo.yarc.features.posts.infrastructure.model.PostResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class PostsRepository @Inject constructor(
    private val postsPagingSourceFactory: PostsPagingSourceFactory,
    private val pagingConfig: PagingConfig,
    private val mapper: Mapper<PostResponse, Post>
) {
    fun fetchPosts(subreddit: String): Flow<PagingData<Post>> {
        val datasource = Pager(
            config = pagingConfig,
            pagingSourceFactory = { postsPagingSourceFactory.create(subreddit) }
        ).flow

        return datasource.map { it.map { response -> mapper.map(response) } }
    }
}