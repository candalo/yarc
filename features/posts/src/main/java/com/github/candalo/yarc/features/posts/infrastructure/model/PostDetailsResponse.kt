package com.github.candalo.yarc.features.posts.infrastructure.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class PostDetailsResponse(
    @SerialName("data")
    val data: PostDetailsDataResponse
)
