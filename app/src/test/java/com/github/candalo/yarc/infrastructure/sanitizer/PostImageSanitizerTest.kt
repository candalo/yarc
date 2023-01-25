package com.github.candalo.yarc.infrastructure.sanitizer

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Test

internal class PostImageSanitizerTest {
    @Test
    fun `sanitize should return correct value`() {
        val mediaAddress = "https://external-preview.redd.it/LOul1q7o2r0eNgaP_2QyL_sfANNfGaCmHcDc7CLW9uE.jpg?auto=webp&amp;s=dd30057d288b2d863c60f4e0d01e0589cfe5ae3e"
        val expectedMediaAddress = "https://external-preview.redd.it/LOul1q7o2r0eNgaP_2QyL_sfANNfGaCmHcDc7CLW9uE.jpg?auto=webp&s=dd30057d288b2d863c60f4e0d01e0589cfe5ae3e"

        val sanitizer = PostImageSanitizer()

        assertThat(sanitizer.sanitize(mediaAddress)).isEqualTo(expectedMediaAddress)
    }
}