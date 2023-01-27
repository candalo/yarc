package com.github.candalo.yarc.features.posts.infrastructure.mapper

import com.github.candalo.yarc.features.posts.infrastructure.postCommentDataResponse
import com.github.candalo.yarc.features.posts.infrastructure.postCommentDataResponseWithReplies
import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

internal class PostCommentMapperTest {
    @Test
    fun `map should convert PostCommentDataResponse to TreeNode PostComment`() {
        val postCommentTreeNode = PostCommentMapper().map(postCommentDataResponse)

        assertThat(postCommentTreeNode.value.body).isEqualTo("Hello darkness my old friend")
        assertThat(postCommentTreeNode.value.authorName).isEqualTo("candalo")
        assertThat(postCommentTreeNode.value.upvotesCount).isEqualTo(100)
        assertThat(postCommentTreeNode.value.publicationElapsedTime).isEqualTo("1h")
        assertThat(postCommentTreeNode.children).isEmpty()
    }

    @Test
    fun `map should convert PostCommentDataResponse with children to TreeNode PostComment`() {
        val postCommentTreeNode = PostCommentMapper().map(postCommentDataResponseWithReplies)

        assertThat(postCommentTreeNode.value.body).isEqualTo("Hello darkness my old friend")
        assertThat(postCommentTreeNode.value.authorName).isEqualTo("candalo")
        assertThat(postCommentTreeNode.value.upvotesCount).isEqualTo(100)
        assertThat(postCommentTreeNode.value.publicationElapsedTime).isEqualTo("1h")
        assertThat(postCommentTreeNode.children).isNotEmpty()
        assertThat(postCommentTreeNode.children.first().value.body).isEqualTo("I've come to talk with you again")
        assertThat(postCommentTreeNode.children.first().value.authorName).isEqualTo("oladnac")
        assertThat(postCommentTreeNode.children.first().value.upvotesCount).isEqualTo(50)
        assertThat(postCommentTreeNode.children.first().value.publicationElapsedTime).isEqualTo("1h")
        assertThat(postCommentTreeNode.children.first().children).isEmpty()
    }
}