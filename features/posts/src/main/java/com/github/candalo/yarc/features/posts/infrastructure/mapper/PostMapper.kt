package com.github.candalo.yarc.features.posts.infrastructure.mapper

import com.github.candalo.yarc.converter.Mapper
import com.github.candalo.yarc.converter.Sanitizer
import com.github.candalo.yarc.features.posts.domain.model.Post
import com.github.candalo.yarc.features.posts.domain.model.PostMedia
import com.github.candalo.yarc.features.posts.infrastructure.PostImageSanitizerType
import com.github.candalo.yarc.features.posts.infrastructure.formatter.toElapsedDate
import com.github.candalo.yarc.features.posts.infrastructure.model.PostResponse
import javax.inject.Inject

internal class PostMapper @Inject constructor(
    @PostImageSanitizerType private val imageSanitizer: Sanitizer<String>
) : Mapper<PostResponse, Post> {
    override fun map(input: PostResponse): Post =
        Post(
            input.data.id,
            input.data.title,
            input.data.description,
            input.data.authorName,
            input.data.upvotesCount,
            input.data.commentsCount,
            input.data.timestamp.toElapsedDate(),
            input.data.permalink,
            PostMedia(
                if (input.data.thumbnailUrl == "self") null
                else input.data.thumbnailUrl,

                if (input.data.preview == null) null
                else imageSanitizer.sanitize(input.data.preview.images.first().source.imageUrl)
            )
        )
}