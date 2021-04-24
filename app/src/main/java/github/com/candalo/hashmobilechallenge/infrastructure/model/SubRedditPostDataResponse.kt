package github.com.candalo.hashmobilechallenge.infrastructure.model

import github.com.candalo.hashmobilechallenge.infrastructure.serializer.InstantSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
internal data class SubRedditPostDataResponse(
    @SerialName("name")
    val id: String,
    @SerialName("title")
    val title: String,
    @SerialName("selftext")
    val description: String,
    @SerialName("author")
    val authorName: String,
    @SerialName("ups")
    val upvotesCount: Int,
    @SerialName("num_comments")
    val commentsCount: Int,
    @SerialName("created_utc")
    @Serializable(InstantSerializer::class)
    val timestamp: Instant,
    @SerialName("thumbnail")
    val thumbnailUrl: String? = null,
    @SerialName("url_overridden_by_dest")
    val mediaUrl: String? = null
)
