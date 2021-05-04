package github.com.candalo.yarc.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import github.com.candalo.yarc.R
import github.com.candalo.yarc.databinding.FragmentPostDetailsBinding
import github.com.candalo.yarc.presentation.adapter.PostCommentExpandableGroup
import github.com.candalo.yarc.presentation.extensions.loadImage
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostDetailsFragment : Fragment(R.layout.fragment_post_details) {
    private val binding: FragmentPostDetailsBinding by viewBinding()
    private val viewModel: PostDetailsViewModel by viewModel()
    private val args: PostDetailsFragmentArgs by navArgs()
    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()
        populateScreen()
        populateComments()
    }

    private fun configureRecyclerView() {
        binding.rvPostComments.apply {
            layoutManager = GridLayoutManager(requireContext(), groupAdapter.spanCount).apply {
                spanSizeLookup = groupAdapter.spanSizeLookup
            }
            adapter = groupAdapter
        }
    }

    private fun populateScreen() {
        with(binding) {
            ivPostMedia.loadImage(requireContext(), args.post.media.mediaUrl)
            tvPostTitle.text = args.post.title
            tvPostDescription.text = args.post.description
            tvPostAuthor.text = args.post.authorName
            tvPostUpvotesCount.text = args.post.upvotesCount.toString()
            tvPostCommentsCount.text = args.post.commentsCount.toString()
            tvPostCreationDate.text = args.post.publicationElapsedTime
        }
    }

    private fun populateComments() {
        lifecycleScope.launch {
            viewModel.fetchComments(args.post.permalink).observe(viewLifecycleOwner) {
                binding.pbPostComments.hide()
                groupAdapter.addAll(it.map(::PostCommentExpandableGroup))
            }
        }
    }
}