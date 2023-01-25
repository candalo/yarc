package com.github.candalo.yarc.infrastructure.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PostCommentResponse(
    @SerialName("data")
    val data: PostCommentDataResponse
)
