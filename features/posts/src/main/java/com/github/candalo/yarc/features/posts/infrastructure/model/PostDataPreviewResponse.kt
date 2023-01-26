package com.github.candalo.yarc.features.posts.infrastructure.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class PostDataPreviewResponse(
    @SerialName("images")
    val images: List<PostDataPreviewImageResponse>
)
