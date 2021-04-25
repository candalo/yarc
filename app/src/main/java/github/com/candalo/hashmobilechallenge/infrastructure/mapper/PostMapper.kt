package github.com.candalo.hashmobilechallenge.infrastructure.mapper

import android.content.Context
import github.com.candalo.hashmobilechallenge.domain.model.Post
import github.com.candalo.hashmobilechallenge.domain.model.PostMedia
import github.com.candalo.hashmobilechallenge.infrastructure.model.PostResponse
import github.com.candalo.hashmobilechallenge.presentation.extensions.toElapsedDate

internal class PostMapper(private val context: Context) {
    fun map(postResponse: PostResponse): Post =
        Post(
            postResponse.data.id,
            postResponse.data.title,
            postResponse.data.description,
            postResponse.data.authorName,
            postResponse.data.upvotesCount,
            postResponse.data.commentsCount,
            postResponse.data.timestamp.toElapsedDate(context),
            PostMedia(
                if (postResponse.data.thumbnailUrl == "self") null else postResponse.data.thumbnailUrl,
                postResponse.data.mediaUrl
            )
        )
}