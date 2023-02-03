package com.github.candalo.yarc.features.posts.infrastructure.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.github.candalo.yarc.converter.Mapper
import com.github.candalo.yarc.converter.Sanitizer
import com.github.candalo.yarc.features.posts.domain.model.Post
import com.github.candalo.yarc.features.posts.domain.model.PostComment
import com.github.candalo.yarc.features.posts.domain.model.TreeNode
import com.github.candalo.yarc.features.posts.infrastructure.PostsService
import com.github.candalo.yarc.features.posts.infrastructure.datasource.PostsPagingSourceFactory
import com.github.candalo.yarc.features.posts.infrastructure.model.PostCommentDataResponse
import com.github.candalo.yarc.features.posts.infrastructure.model.PostResponse
import com.github.candalo.yarc.features.posts.infrastructure.post
import com.github.candalo.yarc.features.posts.infrastructure.postComment
import com.github.candalo.yarc.features.posts.infrastructure.postCommentReply
import com.github.candalo.yarc.features.posts.infrastructure.postDetailsResponse
import com.github.candalo.yarc.features.posts.infrastructure.postResponse
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class PostsRepositoryTest {
    private val pagingData = PagingData.from(listOf(postResponse))
    private val datasource = flowOf(pagingData)

    private val postsPagingSourceFactory = mockk<PostsPagingSourceFactory>()
    private val pagingConfig = mockk<PagingConfig>()

    private val postMapper = mockk<Mapper<PostResponse, Post>>()
    private val postCommentsMapper = mockk<Mapper<PostCommentDataResponse, TreeNode<PostComment>>>()

    private val service = mockk<PostsService>()
    private val postsPaging = PostsPaging(postsPagingSourceFactory, pagingConfig)
    private val postsMapper = PostsMapper(postMapper, postCommentsMapper)
    private val endpointSanitizer = mockk<Sanitizer<String>>()
    private val repository = PostsRepository(service, postsPaging, postsMapper, endpointSanitizer)

    @Test
    fun `fetchPosts should get paging data with post response and map it to paging data with post`() = runTest {
        val subreddit = "androiddev"

        mockkConstructor(Pager::class)
        every { anyConstructed<Pager<String, PostResponse>>().flow } returns datasource
        every { postsMapper.mapper.map(postResponse) } returns post

        repository.fetchPosts(subreddit).collect {
            assertThat(it).isNotNull()
        }
    }

    @Test
    fun `fetchComments should get post comments and map it to a tree node`() = runTest {
        val permalink = "permalink123"
        val sanitizedPermalink = "permalink"
        val postCommentTreeNode = TreeNode(postComment)
        val postCommentWithRepliesTreeNode = TreeNode(postComment).apply { add(TreeNode(postCommentReply)) }

        every {
            endpointSanitizer.sanitize(permalink)
        } returns sanitizedPermalink

        coEvery {
            service.getPostComments(sanitizedPermalink)
        } returns listOf(postDetailsResponse)

        every {
            postsMapper.commentsMapper.map(
                postDetailsResponse.data.postCommentsResponse.first().data
            )
        } returns postCommentTreeNode

        every {
            postsMapper.commentsMapper.map(
                postDetailsResponse.data.postCommentsResponse.last().data
            )
        } returns postCommentWithRepliesTreeNode

        repository.fetchPostComments(permalink).collect {
            assertThat(it).hasSize(2)
            assertThat(it).contains(postCommentTreeNode)
            assertThat(it).contains(postCommentWithRepliesTreeNode)
        }
    }
}