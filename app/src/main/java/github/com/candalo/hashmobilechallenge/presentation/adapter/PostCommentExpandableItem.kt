package github.com.candalo.hashmobilechallenge.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import com.xwray.groupie.ExpandableGroup
import com.xwray.groupie.ExpandableItem
import com.xwray.groupie.viewbinding.BindableItem
import github.com.candalo.hashmobilechallenge.R
import github.com.candalo.hashmobilechallenge.databinding.ItemPostCommentBinding
import github.com.candalo.hashmobilechallenge.domain.model.PostComment
import github.com.candalo.hashmobilechallenge.domain.model.TreeNode

internal class PostCommentExpandableItem(
    private val commentTree: TreeNode<PostComment>,
    private val depth: Int
) : BindableItem<ItemPostCommentBinding>(), ExpandableItem {
    private var expandableGroup: ExpandableGroup? = null

    override fun setExpandableGroup(onToggleListener: ExpandableGroup) {
        expandableGroup = onToggleListener
    }

    override fun getLayout(): Int = R.layout.item_post_comment

    override fun initializeViewBinding(view: View): ItemPostCommentBinding =
        ItemPostCommentBinding.bind(view)

    override fun bind(viewBinding: ItemPostCommentBinding, position: Int) {
        handleViewSeparator(viewBinding)
        populateItem(viewBinding)
    }

    private fun handleViewSeparator(viewBinding: ItemPostCommentBinding) {
        handleViewSeparatorVisibility(viewBinding)
        handleViewSeparatorInflate(viewBinding)
    }

    private fun handleViewSeparatorVisibility(viewBinding: ItemPostCommentBinding) {
        with(viewBinding.viewSeparator) {
            removeAllViews()
            isVisible = depth > 0
        }
    }

    private fun handleViewSeparatorInflate(viewBinding: ItemPostCommentBinding) {
        repeat(depth) {
            val view = LayoutInflater
                .from(viewBinding.root.context)
                .inflate(R.layout.view_separator, viewBinding.viewSeparator, false)

            viewBinding.viewSeparator.addView(view)
        }
    }

    private fun populateItem(viewBinding: ItemPostCommentBinding) {
        with(viewBinding) {
            tvPostCommentAuthor.text = commentTree.value.authorName
            tvPostCommentUpvotesCount.text = commentTree.value.upvotesCount.toString()
            tvPostCommentCreationDate.text = commentTree.value.publicationElapsedTime
            tvPostCommentBody.text = commentTree.value.body
            root.setOnClickListener { expandableGroup?.onToggleExpanded() }
        }
    }
}