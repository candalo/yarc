package github.com.candalo.hashmobilechallenge.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import github.com.candalo.hashmobilechallenge.databinding.FragmentPostDetailsBinding

class PostDetailsFragment : Fragment() {
    private var _binding: FragmentPostDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: PostDetailsFragmentArgs by navArgs()

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
        populateScreen()
    }

    private fun populateScreen() {
        binding.tvPostTitle.text = args.subRedditPost.title
        binding.tvPostDescription.text = args.subRedditPost.description
        binding.tvPostAuthor.text = args.subRedditPost.authorName
        binding.tvPostUpvotesCount.text = args.subRedditPost.upvotesCount.toString()
        binding.tvPostCommentsCount.text = args.subRedditPost.commentsCount.toString()
        binding.tvPostCreationDate.text = args.subRedditPost.publicationElapsedTime
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}