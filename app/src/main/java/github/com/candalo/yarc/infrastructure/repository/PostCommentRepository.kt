package github.com.candalo.yarc.infrastructure.repository

import github.com.candalo.yarc.domain.model.PostComment
import github.com.candalo.yarc.domain.model.TreeNode
import github.com.candalo.yarc.infrastructure.api.Endpoints
import github.com.candalo.yarc.infrastructure.mapper.Mapper
import github.com.candalo.yarc.infrastructure.model.PostCommentDataResponse
import github.com.candalo.yarc.infrastructure.sanitizer.Sanitizer
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