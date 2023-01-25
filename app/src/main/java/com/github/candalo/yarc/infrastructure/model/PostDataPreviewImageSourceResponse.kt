package com.github.candalo.yarc.infrastructure.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PostDataPreviewImageSourceResponse(
    @SerialName("url")
    val imageUrl: String
)
