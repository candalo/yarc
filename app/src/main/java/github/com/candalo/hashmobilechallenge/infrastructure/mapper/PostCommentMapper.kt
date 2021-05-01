package github.com.candalo.hashmobilechallenge.infrastructure.mapper

import android.content.Context
import github.com.candalo.hashmobilechallenge.domain.model.PostComment
import github.com.candalo.hashmobilechallenge.domain.model.TreeNode
import github.com.candalo.hashmobilechallenge.infrastructure.model.PostCommentDataResponse
import github.com.candalo.hashmobilechallenge.presentation.extensions.toElapsedDate

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