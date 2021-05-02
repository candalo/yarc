package github.com.candalo.yarc.infrastructure.mapper

import android.content.Context
import com.google.common.truth.Truth.assertThat
import github.com.candalo.yarc.infrastructure.instant
import github.com.candalo.yarc.infrastructure.postCommentDataResponse
import github.com.candalo.yarc.infrastructure.postCommentDataResponseWithReplies
import github.com.candalo.yarc.presentation.extensions.toElapsedDate
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

internal class PostCommentMapperTest {
    private val context = mockk<Context>()

    @Test
    fun `map should convert PostCommentDataResponse to TreeNode PostComment`() {
        every { instant.toElapsedDate(context) } returns "1h"

        val postCommentTreeNode = PostCommentMapper(context).map(postCommentDataResponse)

        assertThat(postCommentTreeNode.value.body).isEqualTo("Hello darkness my old friend")
        assertThat(postCommentTreeNode.value.authorName).isEqualTo("candalo")
        assertThat(postCommentTreeNode.value.upvotesCount).isEqualTo(100)
        assertThat(postCommentTreeNode.value.publicationElapsedTime).isEqualTo("1h")
        assertThat(postCommentTreeNode.children).isEmpty()
    }

    @Test
    fun `map should convert PostCommentDataResponse with children to TreeNode PostComment`() {
        every { instant.toElapsedDate(context) } returns "1h"

        val postCommentTreeNode = PostCommentMapper(context).map(postCommentDataResponseWithReplies)

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