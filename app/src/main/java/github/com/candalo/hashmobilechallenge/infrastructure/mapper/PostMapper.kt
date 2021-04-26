package github.com.candalo.hashmobilechallenge.infrastructure.mapper

import android.content.Context
import github.com.candalo.hashmobilechallenge.domain.model.Post
import github.com.candalo.hashmobilechallenge.domain.model.PostMedia
import github.com.candalo.hashmobilechallenge.infrastructure.model.PostResponse
import github.com.candalo.hashmobilechallenge.presentation.extensions.toElapsedDate

internal class PostMapper(private val context: Context) : Mapper<PostResponse, Post> {
    override fun map(input: PostResponse): Post =
        Post(
            input.data.id,
            input.data.title,
            input.data.description,
            input.data.authorName,
            input.data.upvotesCount,
            input.data.commentsCount,
            input.data.timestamp.toElapsedDate(context),
            PostMedia(
                if (input.data.thumbnailUrl == "self") null else input.data.thumbnailUrl,
                input.data.mediaUrl
            )
        )

}