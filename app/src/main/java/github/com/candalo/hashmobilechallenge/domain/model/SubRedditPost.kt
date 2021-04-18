package github.com.candalo.hashmobilechallenge.domain.model

import java.time.Instant

internal data class SubRedditPost(
    val id: String,
    val title: String,
    val authorName: String,
    val publicationTimestamp: Instant,
    val media: SubRedditPostMedia
)
