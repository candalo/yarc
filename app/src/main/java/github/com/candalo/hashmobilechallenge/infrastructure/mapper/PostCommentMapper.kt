package github.com.candalo.hashmobilechallenge.infrastructure.mapper

import github.com.candalo.hashmobilechallenge.domain.model.PostComment
import github.com.candalo.hashmobilechallenge.domain.model.TreeNode
import github.com.candalo.hashmobilechallenge.infrastructure.model.PostCommentDataResponse

internal class PostCommentMapper {
    fun map(postCommentDataResponse: PostCommentDataResponse): TreeNode<PostComment> =
        TreeNode(
            PostComment(postCommentDataResponse.body, postCommentDataResponse.authorName)
        ).apply {
            if (postCommentDataResponse.replies == null) {
                return this
            }
            postCommentDataResponse.replies.data.postCommentsResponse.forEach {
                add(map(it.data))
            }
        }
}