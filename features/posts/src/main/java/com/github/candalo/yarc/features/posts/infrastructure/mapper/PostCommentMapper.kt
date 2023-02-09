package com.github.candalo.yarc.features.posts.infrastructure.mapper

import com.github.candalo.yarc.converter.Mapper
import com.github.candalo.yarc.features.posts.domain.model.PostComment
import com.github.candalo.yarc.features.posts.domain.model.TreeNode
import com.github.candalo.yarc.features.posts.infrastructure.formatter.toElapsedDate
import com.github.candalo.yarc.features.posts.infrastructure.model.PostCommentDataResponse
import javax.inject.Inject

internal class PostCommentMapper @Inject constructor() : Mapper<PostCommentDataResponse, TreeNode<PostComment>> {
    override fun map(input: PostCommentDataResponse): TreeNode<PostComment> =
        TreeNode(
            PostComment(
                input.id,
                input.body ?: "",
                input.authorName,
                input.upvotesCount,
                input.timestamp.toElapsedDate()
            )
        ).apply {
            if (input.replies == null) {
                return this
            }
            input.replies.data.postCommentsResponse.forEach {
                add(map(it.data))
            }
        }
}