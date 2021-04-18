package github.com.candalo.hashmobilechallenge.infrastructure.mapper

import github.com.candalo.hashmobilechallenge.domain.model.SubRedditPost
import github.com.candalo.hashmobilechallenge.domain.model.SubRedditPostMedia
import github.com.candalo.hashmobilechallenge.infrastructure.model.SubRedditPostResponse

internal class PostMapper {
    fun map(postResponse: SubRedditPostResponse): SubRedditPost =
        SubRedditPost(
            postResponse.data.id,
            postResponse.data.title,
            postResponse.data.authorName,
            postResponse.data.timestamp,
            SubRedditPostMedia(
                postResponse.data.thumbnailUrl,
                postResponse.data.mediaUrl
            )
        )
}