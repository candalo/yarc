package github.com.candalo.hashmobilechallenge.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import github.com.candalo.hashmobilechallenge.databinding.FragmentPostsBinding
import github.com.candalo.hashmobilechallenge.presentation.adapter.PostsAdapter
import github.com.candalo.hashmobilechallenge.presentation.adapter.PostsLoadStateAdapter
import org.koin.android.ext.android.inject

internal class PostsFragment : Fragment() {
    private var _binding: FragmentPostsBinding? = null
    private val binding get() = _binding!!

    private val postsAdapter: PostsAdapter by inject()
    private val postsLoadStateAdapter: PostsLoadStateAdapter by inject()

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
        binding.rvPosts.apply {

        }
    }

    private fun configureAdapter() {
        binding.rvPosts.apply {
            adapter = postsAdapter.apply { withLoadStateFooter(postsLoadStateAdapter) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}