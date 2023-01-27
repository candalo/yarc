package com.github.candalo.yarc.features.posts.infrastructure.mapper

import com.github.candalo.yarc.converter.Sanitizer
import com.github.candalo.yarc.features.posts.infrastructure.postResponse
import com.github.candalo.yarc.features.posts.infrastructure.postResponseWithImagePreview
import com.github.candalo.yarc.features.posts.infrastructure.postResponseWithThumbnailUrl
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

internal class PostMapperTest {
    private val imageSanitizer = mockk<Sanitizer<String>>()

    @Test
    fun `map should convert PostResponse to Post`() {
        val post = PostMapper(imageSanitizer).map(postResponse)

        assertThat(post.id).isEqualTo("1")
        assertThat(post.title).isEqualTo("Android Studio is buggy")
        assertThat(post.description).isEqualTo("Android Studio is very very buggy")
        assertThat(post.authorName).isEqualTo("candalo")
        assertThat(post.upvotesCount).isEqualTo(1000)
        assertThat(post.commentsCount).isEqualTo(100)
        assertThat(post.publicationElapsedTime).isEqualTo("1h")
        assertThat(post.permalink).isEqualTo("permalink")
        assertThat(post.media.thumbnailUrl).isNull()
        assertThat(post.media.mediaUrl).isNull()
    }

    @Test
    fun `map should convert PostResponse with thumbnail url to Post`() {
        val post = PostMapper(imageSanitizer).map(postResponseWithThumbnailUrl)

        assertThat(post.id).isEqualTo("1")
        assertThat(post.title).isEqualTo("Android Studio is buggy")
        assertThat(post.description).isEqualTo("Android Studio is very very buggy")
        assertThat(post.authorName).isEqualTo("candalo")
        assertThat(post.upvotesCount).isEqualTo(1000)
        assertThat(post.commentsCount).isEqualTo(100)
        assertThat(post.publicationElapsedTime).isEqualTo("1h")
        assertThat(post.permalink).isEqualTo("permalink")
        assertThat(post.media.thumbnailUrl).isEqualTo("https://www.thumbnail.com/")
        assertThat(post.media.mediaUrl).isNull()
    }

    @Test
    fun `map should convert PostResponse with image preview to Post`() {
        every { imageSanitizer.sanitize("https://www.imageUrl123.com/") } returns "https://www.imageUrl.com/"

        val post = PostMapper(imageSanitizer).map(postResponseWithImagePreview)

        assertThat(post.id).isEqualTo("1")
        assertThat(post.title).isEqualTo("Android Studio is buggy")
        assertThat(post.description).isEqualTo("Android Studio is very very buggy")
        assertThat(post.authorName).isEqualTo("candalo")
        assertThat(post.upvotesCount).isEqualTo(1000)
        assertThat(post.commentsCount).isEqualTo(100)
        assertThat(post.publicationElapsedTime).isEqualTo("1h")
        assertThat(post.permalink).isEqualTo("permalink")
        assertThat(post.media.thumbnailUrl).isNull()
        assertThat(post.media.mediaUrl).isEqualTo("https://www.imageUrl.com/")
    }
}