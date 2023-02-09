package com.github.candalo.yarc.features.posts.domain.model

internal class TreeNode<T>(val value: T) {
    private var parent: TreeNode<T>? = null
    val children: MutableList<TreeNode<T>> = mutableListOf()

    fun hasChildren(): Boolean = children.isNotEmpty()

    fun add(child: TreeNode<T>) {
        children.add(child)
        child.parent = this
    }
}