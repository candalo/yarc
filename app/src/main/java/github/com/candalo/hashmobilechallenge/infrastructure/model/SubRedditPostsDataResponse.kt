package github.com.candalo.hashmobilechallenge.infrastructure.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SubRedditPostsDataResponse(
    @SerialName("dist")
    val itemsCount: Int,
    @SerialName("children")
    val posts: List<SubRedditPostResponse>,
    @SerialName("before")
    val before: String,
    @SerialName("after")
    val after: String
)
