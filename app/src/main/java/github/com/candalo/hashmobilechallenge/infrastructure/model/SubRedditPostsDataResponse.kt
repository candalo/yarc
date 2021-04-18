package github.com.candalo.hashmobilechallenge.infrastructure.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SubRedditPostsDataResponse(
    @SerialName("dist")
    val itemsCount: Int,
    @SerialName("children")
    val post: SubRedditPostResponse
)
