package github.com.candalo.hashmobilechallenge.infrastructure.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PostDataPreviewImageResponse(
    @SerialName("source")
    val source: PostDataPreviewImageSourceResponse
)
