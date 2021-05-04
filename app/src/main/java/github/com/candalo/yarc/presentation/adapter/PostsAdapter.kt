package github.com.candalo.yarc.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import github.com.candalo.yarc.R
import github.com.candalo.yarc.databinding.ItemPostBinding
import github.com.candalo.yarc.domain.model.Post
import github.com.candalo.yarc.presentation.extensions.loadImage

internal class PostsAdapter(
    private val onPostSelected: (Post) -> Unit
) : PagingDataAdapter<Post, PostsViewHolder>(PostsComparator) {
    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) =
        holder.bind(getItem(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder =
        PostsViewHolder(parent, onPostSelected)
}

internal class PostsViewHolder(
    private val parent: ViewGroup,
    private val onPostSelected: (Post) -> Unit
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
) {
    private val binding = ItemPostBinding.bind(itemView)

    fun bind(post: Post?) {
        post?.let {
            it.populateItemData()
            it.configureItemClick()
        }
    }

    private fun Post.populateItemData() {
        with(binding) {
            if (this@populateItemData.media.thumbnailUrl != null) {
                ivPostMediaThumbnail.loadImage(parent.context, this@populateItemData.media.thumbnailUrl)
            } else {
                ivPostMediaThumbnail.setImageResource(R.drawable.outline_article_24)
            }
            tvPostTitle.text = this@populateItemData.title
            tvPostAuthor.text = this@populateItemData.authorName
            tvPostCreationDate.text = this@populateItemData.publicationElapsedTime
            tvPostCommentsCount.text = this@populateItemData.commentsCount.toString()
            tvPostUpvotesCount.text = this@populateItemData.upvotesCount.toString()
        }
    }

    private fun Post.configureItemClick() {
        itemView.setOnClickListener {
            onPostSelected(this)
        }
    }
}

internal object PostsComparator : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
        oldItem == newItem
}