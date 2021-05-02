package github.com.candalo.yarc.infrastructure.repository

import com.google.common.truth.Truth.assertThat
import github.com.candalo.yarc.domain.model.PostComment
import github.com.candalo.yarc.domain.model.TreeNode
import github.com.candalo.yarc.infrastructure.api.Endpoints
import github.com.candalo.yarc.infrastructure.mapper.Mapper
import github.com.candalo.yarc.infrastructure.model.PostCommentDataResponse
import github.com.candalo.yarc.infrastructure.postComment
import github.com.candalo.yarc.infrastructure.postCommentReply
import github.com.candalo.yarc.infrastructure.postDetailsResponse
import github.com.candalo.yarc.infrastructure.sanitizer.Sanitizer
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test

internal class PostCommentRepositoryTest {
    private val endpoints = mockk<Endpoints>()
    private val endpointSanitizer = mockk<Sanitizer<String>>()
    private val mapper = mockk<Mapper<PostCommentDataResponse, TreeNode<PostComment>>>()
    private val repository = PostCommentRepository(endpoints, endpointSanitizer, mapper)

    @Test
    fun `fetchComments should get post comments and map it to a tree node`() = runBlockingTest {
        val permalink = "permalink123"
        val sanitizedPermalink = "permalink"
        val postCommentTreeNode = TreeNode(postComment)
        val postCommentWithRepliesTreeNode = TreeNode(postComment).apply { add(TreeNode(postCommentReply)) }

        every {
            endpointSanitizer.sanitize(permalink)
        } returns sanitizedPermalink

        coEvery {
            endpoints.getComments(sanitizedPermalink)
        } returns listOf(postDetailsResponse)

        every {
            mapper.map(postDetailsResponse.data.postCommentsResponse.first().data)
        } returns postCommentTreeNode

        every {
            mapper.map(postDetailsResponse.data.postCommentsResponse.last().data)
        } returns postCommentWithRepliesTreeNode

        repository.fetchComments(permalink).collect {
            assertThat(it).hasSize(2)
            assertThat(it).contains(postCommentTreeNode)
            assertThat(it).contains(postCommentWithRepliesTreeNode)
        }
    }
}