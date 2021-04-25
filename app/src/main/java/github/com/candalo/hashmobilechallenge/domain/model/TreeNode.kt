package github.com.candalo.hashmobilechallenge.domain.model

internal class TreeNode<T>(val value: T) {
    private var parent: TreeNode<T>? = null
    private val children: MutableList<TreeNode<T>> = mutableListOf()

    fun add(child: TreeNode<T>) {
        children.add(child)
        child.parent = this
    }
}