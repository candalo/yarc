package com.github.candalo.yarc.features.posts.ui

import androidx.lifecycle.ViewModel
import com.github.candalo.yarc.features.posts.domain.model.PostComment
import com.github.candalo.yarc.features.posts.domain.model.TreeNode
import com.github.candalo.yarc.features.posts.infrastructure.repository.PostsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
internal class PostDetailsViewModel @Inject constructor(
    private val repository: PostsRepository
) : ViewModel() {
    fun getPostDetails(
        permalink: String
    ): Flow<List<TreeNode<PostComment>>> = repository.fetchPostComments(permalink)
}