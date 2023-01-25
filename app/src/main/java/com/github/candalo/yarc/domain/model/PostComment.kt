package com.github.candalo.yarc.domain.model

internal data class PostComment(
    val body: String,
    val authorName: String,
    val upvotesCount: Int,
    val publicationElapsedTime: String
)