package github.com.candalo.hashmobilechallenge.domain.model

import kotlinx.serialization.Serializable as KSerializable
import java.io.Serializable

@KSerializable
data class Post(
    val id: String,
    val title: String,
    val description: String,
    val authorName: String,
    val upvotesCount: Int,
    val commentsCount: Int,
    val publicationElapsedTime: String,
    val media: PostMedia
) : Serializable
