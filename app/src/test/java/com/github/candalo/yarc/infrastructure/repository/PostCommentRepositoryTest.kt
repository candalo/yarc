package com.github.candalo.yarc.infrastructure.repository

import com.google.common.truth.Truth.assertThat
import com.github.candalo.yarc.domain.model.PostComment
import com.github.candalo.yarc.domain.model.TreeNode
import com.github.candalo.yarc.infrastructure.api.Endpoints
import com.github.candalo.yarc.infrastructure.mapper.Mapper
import com.github.candalo.yarc.infrastructure.model.PostCommentDataResponse
import com.github.candalo.yarc.infrastructure.postComment
import com.github.candalo.yarc.infrastructure.postCommentReply
import com.github.candalo.yarc.infrastructure.postDetailsResponse
import com.github.candalo.yarc.infrastructure.sanitizer.Sanitizer
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