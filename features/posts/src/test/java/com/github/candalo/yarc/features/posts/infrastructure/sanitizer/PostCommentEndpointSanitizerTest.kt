package com.github.candalo.yarc.features.posts.infrastructure.sanitizer

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

internal class PostCommentEndpointSanitizerTest {
    @Test
    fun `sanitize should return correct value`() {
        val postCommentEndpoint = "/r/androiddev/comments/myx3co/weekly_whos_hiring_thread_april_26_2021/"
        val expectedPostCommentEndpoint = "r/androiddev/comments/myx3co/weekly_whos_hiring_thread_april_26_2021.json"

        val sanitizer = PostCommentEndpointSanitizer()

        assertThat(sanitizer.sanitize(postCommentEndpoint)).isEqualTo(expectedPostCommentEndpoint)
    }
}