package github.com.candalo.hashmobilechallenge.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import github.com.candalo.hashmobilechallenge.databinding.FragmentPostsBinding
import github.com.candalo.hashmobilechallenge.domain.model.Post
import github.com.candalo.hashmobilechallenge.presentation.adapter.PostsAdapter
import github.com.candalo.hashmobilechallenge.presentation.adapter.PostsLoadStateAdapter
import kotlinx.coroutines.flow.collectLatest
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

    private fun populatePosts() {
        lifecycleScope.launch {
            viewModel.fetchPosts().collectLatest { postsAdapter.submitData(it) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}