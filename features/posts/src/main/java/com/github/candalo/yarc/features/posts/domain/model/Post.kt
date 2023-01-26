package com.github.candalo.yarc.features.posts.domain.model

import kotlinx.serialization.Serializable

@Serializable
internal data class Post(
    val id: String,
    val title: String,
    val description: String,
    val authorName: String,
    val upvotesCount: Int,
    val commentsCount: Int,
    val publicationElapsedTime: String,
    val permalink: String,
    val media: PostMedia
)
