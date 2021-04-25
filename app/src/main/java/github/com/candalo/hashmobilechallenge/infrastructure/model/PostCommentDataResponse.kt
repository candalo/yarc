package github.com.candalo.hashmobilechallenge.infrastructure.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PostCommentDataResponse(
    @SerialName("body")
    val body: String,
    @SerialName("author")
    val authorName: String,
    @SerialName("replies")
    val replies: PostDetailsResponse? = null
)
