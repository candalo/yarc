package com.github.candalo.yarc.features.posts.infrastructure.model

import com.github.candalo.yarc.features.posts.infrastructure.serializer.InstantSerializer
import com.github.candalo.yarc.features.posts.infrastructure.serializer.PostCommentRepliesSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
internal data class PostCommentDataResponse(
    @SerialName("body")
    val body: String? = null,
    @SerialName("author")
    val authorName: String,
    @SerialName("ups")
    val upvotesCount: Int,
    @SerialName("created_utc")
    @Serializable(InstantSerializer::class)
    val timestamp: Instant,
    @SerialName("replies")
    @Serializable(PostCommentRepliesSerializer::class)
    val replies: PostDetailsResponse? = null
)
