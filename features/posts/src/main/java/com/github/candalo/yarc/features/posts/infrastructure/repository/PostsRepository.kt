package com.github.candalo.yarc.features.posts.infrastructure.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.github.candalo.yarc.converter.Mapper
import com.github.candalo.yarc.converter.Sanitizer
import com.github.candalo.yarc.features.posts.domain.model.Post
import com.github.candalo.yarc.features.posts.domain.model.PostComment
import com.github.candalo.yarc.features.posts.domain.model.TreeNode
import com.github.candalo.yarc.features.posts.infrastructure.PostCommentEndpointSanitizerType
import com.github.candalo.yarc.features.posts.infrastructure.PostsService
import com.github.candalo.yarc.features.posts.infrastructure.datasource.PostsPagingSourceFactory
import com.github.candalo.yarc.features.posts.infrastructure.model.PostCommentDataResponse
import com.github.candalo.yarc.features.posts.infrastructure.model.PostResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal data class PostsPaging @Inject constructor(
    val sourceFactory: PostsPagingSourceFactory,
    val config: PagingConfig,
)

internal data class PostsMapper @Inject constructor(
    val mapper: Mapper<PostResponse, Post>,
    val commentsMapper: Mapper<PostCommentDataResponse, TreeNode<PostComment>>
)

internal class PostsRepository @Inject constructor(
    private val service: PostsService,
    private val postsPaging: PostsPaging,
    private val postsMapper: PostsMapper,
    @PostCommentEndpointSanitizerType private val endpointSanitizer: Sanitizer<String>,
) {
    fun fetchPosts(subreddit: String): Flow<PagingData<Post>> {
        val datasource = Pager(
            config = postsPaging.config,
            pagingSourceFactory = { postsPaging.sourceFactory.create(subreddit) }
        ).flow

        return datasource.map { it.map { response -> postsMapper.mapper.map(response) } }
    }

    fun fetchPostComments(permalink: String): Flow<List<TreeNode<PostComment>>> =
        flow {
            service
                .getPostComments(endpointSanitizer.sanitize(permalink))
                .last()
                .data
                .postCommentsResponse
                .map { postsMapper.commentsMapper.map(it.data) }
        }
}