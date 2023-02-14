package com.github.candalo.yarc.features.posts.infrastructure.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PostCommentResponse(
    @SerialName("kind")
    val kind: String,
    @SerialName("data")
    val data: PostCommentDataResponse
) {
    fun isComment(): Boolean = kind == "t1"
}
