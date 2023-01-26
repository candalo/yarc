package com.github.candalo.yarc.features.posts.infrastructure.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PostDetailsDataResponse(
    @SerialName("children")
    val postCommentsResponse: List<PostCommentResponse>
)