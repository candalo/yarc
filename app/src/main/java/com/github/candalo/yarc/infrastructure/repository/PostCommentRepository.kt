package com.github.candalo.yarc.infrastructure.repository

import com.github.candalo.yarc.domain.model.PostComment
import com.github.candalo.yarc.domain.model.TreeNode
import com.github.candalo.yarc.infrastructure.api.Endpoints
import com.github.candalo.yarc.infrastructure.mapper.Mapper
import com.github.candalo.yarc.infrastructure.model.PostCommentDataResponse
import com.github.candalo.yarc.infrastructure.sanitizer.Sanitizer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

internal class PostCommentRepository(
    private val endpoints: Endpoints,
    private val endpointSanitizer: Sanitizer<String>,
    private val mapper: Mapper<PostCommentDataResponse, TreeNode<PostComment>>
) {
    suspend fun fetchComments(postCommentPermalink: String): Flow<List<TreeNode<PostComment>>> =
        flowOf(
            endpoints
                .getComments(endpointSanitizer.sanitize(postCommentPermalink))
                .last()
                .data
                .postCommentsResponse
                .map { mapper.map(it.data) }
        )
}