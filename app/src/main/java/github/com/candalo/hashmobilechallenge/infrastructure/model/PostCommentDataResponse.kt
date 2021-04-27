package github.com.candalo.hashmobilechallenge.infrastructure.model

import github.com.candalo.hashmobilechallenge.infrastructure.serializer.PostCommentRepliesSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PostCommentDataResponse(
    @SerialName("body")
    val body: String? = null,
    @SerialName("author")
    val authorName: String,
    @SerialName("replies")
    @Serializable(PostCommentRepliesSerializer::class)
    val replies: PostDetailsResponse? = null
)
