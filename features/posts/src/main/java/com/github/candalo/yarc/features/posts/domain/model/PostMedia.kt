package com.github.candalo.yarc.features.posts.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class PostMedia(
    val thumbnailUrl: String?,
    val mediaUrl: String?
)
