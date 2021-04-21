package github.com.candalo.hashmobilechallenge.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import github.com.candalo.hashmobilechallenge.R
import github.com.candalo.hashmobilechallenge.databinding.ItemPostBinding
import github.com.candalo.hashmobilechallenge.domain.model.SubRedditPost
import github.com.candalo.hashmobilechallenge.presentation.extensions.toElapsedDate

internal class PostsAdapter : PagingDataAdapter<SubRedditPost, PostsViewHolder>(PostsComparator) {
    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) =
        holder.bind(getItem(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder =
        PostsViewHolder(parent)
}

internal class PostsViewHolder(
    private val parent: ViewGroup
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
) {
    private val binding = ItemPostBinding.bind(itemView)

    fun bind(post: SubRedditPost?) {
        with(binding) {
            if (post?.media?.thumbnailUrl != null) {
                ivPostMediaThumbnail.isVisible = true
                Glide.with(parent.context).load(post.media.thumbnailUrl).into(ivPostMediaThumbnail)
            } else {
                ivPostMediaThumbnail.isGone = true
            }
            tvPostTitle.text = post?.title
            tvPostAuthor.text = post?.authorName
            tvPostCreationDate.text = post?.publicationTimestamp?.toElapsedDate(parent.context)
            tvPostCommentsCount.text = post?.commentsCount.toString()
            tvPostUpvotesCount.text = post?.upvotesCount.toString()
        }
    }
}

internal object PostsComparator : DiffUtil.ItemCallback<SubRedditPost>() {
    override fun areItemsTheSame(oldItem: SubRedditPost, newItem: SubRedditPost): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: SubRedditPost, newItem: SubRedditPost): Boolean =
        oldItem == newItem
}