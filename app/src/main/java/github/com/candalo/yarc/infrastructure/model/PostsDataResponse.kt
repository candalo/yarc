package github.com.candalo.yarc.infrastructure.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class PostsDataResponse(
    @SerialName("dist")
    val itemsCount: Int,
    @SerialName("children")
    val posts: List<PostResponse>,
    @SerialName("before")
    val before: String?,
    @SerialName("after")
    val after: String
)
