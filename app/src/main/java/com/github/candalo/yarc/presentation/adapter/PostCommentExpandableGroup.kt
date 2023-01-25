package com.github.candalo.yarc.presentation.adapter

import com.xwray.groupie.ExpandableGroup
import com.github.candalo.yarc.domain.model.PostComment
import com.github.candalo.yarc.domain.model.TreeNode

internal class PostCommentExpandableGroup(
    commentTree: TreeNode<PostComment>,
    depth: Int = 0
) : ExpandableGroup(PostCommentExpandableItem(commentTree, depth)) {
    init {
        addGroups(commentTree, depth)
    }

    private fun addGroups(commentTree: TreeNode<PostComment>, depth: Int) {
        commentTree.children.forEach {
            add(PostCommentExpandableGroup(it, depth.plus(1)))
        }
    }
}