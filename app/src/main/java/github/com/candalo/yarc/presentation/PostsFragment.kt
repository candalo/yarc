package github.com.candalo.yarc.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.DividerItemDecoration
import github.com.candalo.yarc.databinding.FragmentPostsBinding
import github.com.candalo.yarc.domain.model.Post
import github.com.candalo.yarc.presentation.adapter.PostsAdapter
import github.com.candalo.yarc.presentation.adapter.PostsLoadStateAdapter
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class PostsFragment : Fragment() {
    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PostsViewModel by viewModel()
    private val postsAdapter: PostsAdapter by inject { parametersOf(onPostSelected) }
    private val postsLoadStateAdapter: PostsLoadStateAdapter by inject()
    private val onPostSelected: (Post) -> Unit = { post ->
        findNavController().navigate(
            PostsFragmentDirections.navigateToPostDetailsFragment(post)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPostsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerView()
        configureAdapter()
        configureLoadingStates()
        populatePosts()
    }

    private fun configureRecyclerView() {
        binding.rvPosts.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )
    }

    private fun configureAdapter() {
        binding.rvPosts.adapter = postsAdapter.withLoadStateFooter(postsLoadStateAdapter)
    }

    private fun configureLoadingStates() {
        postsAdapter.addLoadStateListener {
            binding.pbPosts.isVisible = it.isLoading()
            binding.viewPostsEmptyState.root.isVisible = it.isEmptyState()
            binding.viewErrorState.root.isVisible = it.isErrorState()
        }
    }

    private fun CombinedLoadStates.isEmptyState() =
        this.refresh is LoadState.NotLoading && postsAdapter.itemCount == 0

    private fun CombinedLoadStates.isLoading() =
        this.refresh is LoadState.Loading && postsAdapter.itemCount == 0

    private fun CombinedLoadStates.isErrorState() =
        this.refresh is LoadState.Error && postsAdapter.itemCount == 0

    private fun populatePosts() {
        viewModel.posts.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                postsAdapter.submitData(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}