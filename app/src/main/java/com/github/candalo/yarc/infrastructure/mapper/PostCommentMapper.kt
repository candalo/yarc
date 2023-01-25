package com.github.candalo.yarc.infrastructure.mapper

import android.content.Context
import com.github.candalo.yarc.domain.model.PostComment
import com.github.candalo.yarc.domain.model.TreeNode
import com.github.candalo.yarc.infrastructure.model.PostCommentDataResponse
import com.github.candalo.yarc.presentation.extensions.toElapsedDate

internal class PostCommentMapper(
    private val context: Context
) : Mapper<PostCommentDataResponse, TreeNode<PostComment>> {
    override fun map(input: PostCommentDataResponse): TreeNode<PostComment> =
        TreeNode(
            PostComment(
                input.body ?: "",
                input.authorName,
                input.upvotesCount,
                input.timestamp.toElapsedDate(context)
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