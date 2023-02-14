package com.github.candalo.yarc.features.posts.infrastructure.model

import com.github.candalo.yarc.features.posts.infrastructure.serializer.InstantSerializer
import com.github.candalo.yarc.features.posts.infrastructure.serializer.PostCommentRepliesSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
internal data class PostCommentDataResponse(
    @SerialName("id")
    val id: String,
    @SerialName("body")
    val body: String? = null,
    @SerialName("author")
    val authorName: String? = null,
    @SerialName("ups")
    val upvotesCount: Int = 0,
    @SerialName("created_utc")
    @Serializable(InstantSerializer::class)
    val timestamp: Instant? = null,
    @SerialName("replies")
    @Serializable(PostCommentRepliesSerializer::class)
    val replies: PostDetailsResponse? = null
)
