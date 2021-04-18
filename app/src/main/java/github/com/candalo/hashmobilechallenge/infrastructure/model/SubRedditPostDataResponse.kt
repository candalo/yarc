package github.com.candalo.hashmobilechallenge.infrastructure.model

import github.com.candalo.hashmobilechallenge.infrastructure.serializer.InstantSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
internal data class SubRedditPostDataResponse(
    @SerialName("title")
    val title: String,
    @SerialName("author")
    val authorName: String,
    @SerialName("created_utc")
    @Serializable(InstantSerializer::class)
    val timestamp: Instant,
    @SerialName("thumbnail")
    val thumbnailUrl: String,
    @SerialName("url_overriden_by_dest")
    val mediaUrl: String
)
