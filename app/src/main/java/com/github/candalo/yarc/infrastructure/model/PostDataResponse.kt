package com.github.candalo.yarc.infrastructure.model

import com.github.candalo.yarc.infrastructure.serializer.InstantSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
internal data class PostDataResponse(
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
    @SerialName("permalink")
    val permalink: String,
    @SerialName("thumbnail")
    val thumbnailUrl: String? = null,
    @SerialName("preview")
    val preview: PostDataPreviewResponse? = null
)
