package github.com.candalo.hashmobilechallenge.domain.model

internal data class PostCommentTree(
    private val nodes: List<TreeNode<PostComment>>
)
