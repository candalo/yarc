package github.com.candalo.yarc.infrastructure.mapper

import android.content.Context
import github.com.candalo.yarc.domain.model.Post
import github.com.candalo.yarc.domain.model.PostMedia
import github.com.candalo.yarc.infrastructure.model.PostResponse
import github.com.candalo.yarc.infrastructure.sanitizer.Sanitizer
import github.com.candalo.yarc.presentation.extensions.toElapsedDate

internal class PostMapper(
    private val context: Context,
    private val imageSanitizer: Sanitizer<String>
) : Mapper<PostResponse, Post> {
    override fun map(input: PostResponse): Post =
        Post(
            input.data.id,
            input.data.title,
            input.data.description,
            input.data.authorName,
            input.data.upvotesCount,
            input.data.commentsCount,
            input.data.timestamp.toElapsedDate(context),
            input.data.permalink,
            PostMedia(
                if (input.data.thumbnailUrl == "self") null
                else input.data.thumbnailUrl,

                if (input.data.preview == null) null
                else imageSanitizer.sanitize(input.data.preview.images.first().source.imageUrl)
            )
        )

}