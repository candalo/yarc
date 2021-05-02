package github.com.candalo.hashmobilechallenge.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import github.com.candalo.hashmobilechallenge.databinding.FragmentPostDetailsBinding
import github.com.candalo.hashmobilechallenge.presentation.adapter.PostCommentExpandableGroup
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
        if (args.post.media.mediaUrl != null) {
            Glide
                .with(requireContext())
                .load(args.post.media.mediaUrl)
                .into(binding.ivPostMedia)
        }
        binding.tvPostTitle.text = args.post.title
        binding.tvPostDescription.text = args.post.description
        binding.tvPostAuthor.text = args.post.authorName
        binding.tvPostUpvotesCount.text = args.post.upvotesCount.toString()
        binding.tvPostCommentsCount.text = args.post.commentsCount.toString()
        binding.tvPostCreationDate.text = args.post.publicationElapsedTime
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