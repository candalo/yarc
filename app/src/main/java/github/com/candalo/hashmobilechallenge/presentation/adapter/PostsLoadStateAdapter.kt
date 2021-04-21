package github.com.candalo.hashmobilechallenge.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import github.com.candalo.hashmobilechallenge.R
import github.com.candalo.hashmobilechallenge.databinding.ItemPostLoadingBinding

internal class PostsLoadStateAdapter : LoadStateAdapter<PostsLoadStateViewHolder>() {

    override fun onBindViewHolder(holder: PostsLoadStateViewHolder, loadState: LoadState) =
            holder.bind(loadState)

    override fun onCreateViewHolder(
            parent: ViewGroup,
            loadState: LoadState
    ): PostsLoadStateViewHolder = PostsLoadStateViewHolder(parent)
}

internal class PostsLoadStateViewHolder(
        parent: ViewGroup
) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_post_loading, parent, false)
) {
    private val binding = ItemPostLoadingBinding.bind(itemView)

    fun bind(loadState: LoadState) {
        binding.pbPosts.isVisible = loadState is LoadState.Loading
    }
}