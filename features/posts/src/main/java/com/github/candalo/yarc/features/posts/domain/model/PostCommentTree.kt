package com.github.candalo.yarc.features.posts.domain.model

internal data class PostCommentTree(
    private val nodes: List<TreeNode<PostComment>>
)
