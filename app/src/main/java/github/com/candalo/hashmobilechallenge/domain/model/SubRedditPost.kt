package github.com.candalo.hashmobilechallenge.domain.model

import java.time.Instant

internal data class SubRedditPost(
    val id: String,
    val title: String,
    val description: String,
    val authorName: String,
    val upvotesCount: Int,
    val commentsCount: Int,
    val publicationTimestamp: Instant,
    val media: SubRedditPostMedia
)
