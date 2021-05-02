package github.com.candalo.yarc.domain.model

internal data class PostCommentTree(
    private val nodes: List<TreeNode<PostComment>>
)
