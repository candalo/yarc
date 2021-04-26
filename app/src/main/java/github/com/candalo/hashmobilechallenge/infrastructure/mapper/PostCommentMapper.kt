package github.com.candalo.hashmobilechallenge.infrastructure.mapper

import github.com.candalo.hashmobilechallenge.domain.model.PostComment
import github.com.candalo.hashmobilechallenge.domain.model.TreeNode
import github.com.candalo.hashmobilechallenge.infrastructure.model.PostCommentDataResponse

internal class PostCommentMapper : Mapper<PostCommentDataResponse, TreeNode<PostComment>> {
    override fun map(input: PostCommentDataResponse): TreeNode<PostComment> =
        TreeNode(
            PostComment(input.body, input.authorName)
        ).apply {
            if (input.replies == null) {
                return this
            }
            input.replies.data.postCommentsResponse.forEach {
                add(map(it.data))
            }
        }
}