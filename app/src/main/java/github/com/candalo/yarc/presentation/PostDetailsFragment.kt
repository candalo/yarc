package github.com.candalo.yarc.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import github.com.candalo.yarc.databinding.FragmentPostDetailsBinding
import github.com.candalo.yarc.presentation.adapter.PostCommentExpandableGroup
import github.com.candalo.yarc.presentation.extensions.loadImage
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class PostDetailsFragment : Fragment() {
    private var _binding: FragmentPostDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PostDetailsViewModel by viewModel()
    private val args: PostDetailsFragmentArgs by navArgs()
    private val groupAdapter = GroupAdapter<GroupieViewHolder>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}