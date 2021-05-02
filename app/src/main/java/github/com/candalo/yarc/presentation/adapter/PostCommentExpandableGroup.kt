package github.com.candalo.yarc.presentation.adapter

import com.xwray.groupie.ExpandableGroup
import github.com.candalo.yarc.domain.model.PostComment
import github.com.candalo.yarc.domain.model.TreeNode

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