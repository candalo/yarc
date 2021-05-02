package github.com.candalo.yarc.infrastructure

import github.com.candalo.yarc.infrastructure.model.*
import java.time.Instant

internal val instant = Instant.parse("2021-01-01T00:00:00.00Z")

internal val postCommentDataResponse = PostCommentDataResponse(
    "Hello darkness my old friend",
    "candalo",
    100,
    instant,
    null
)

internal val postCommentDataResponseWithReplies = postCommentDataResponse.copy(
    replies = PostDetailsResponse(
        PostDetailsDataResponse(
            listOf(
                PostCommentResponse(
                    PostCommentDataResponse(
                        "I've come to talk with you again",
                        "oladnac",
                        50,
                        instant,
                        null
                    )
                )
            )
        )
    )
)

internal val postResponse = PostResponse(
    PostDataResponse(
        "1",
        "Android Studio is buggy",
        "Android Studio is very very buggy",
        "candalo",
        1000,
        100,
        instant,
        "permalink",
        null,
        null
    )
)

internal val postResponseWithThumbnailUrl = postResponse.copy(
    data = postResponse.data.copy(
        thumbnailUrl = "https://www.thumbnail.com/"
    )
)

internal val postResponseWithImagePreview = postResponse.copy(
    data = postResponse.data.copy(
        preview = PostDataPreviewResponse(
            listOf(
                PostDataPreviewImageResponse(
                    PostDataPreviewImageSourceResponse(
                        "https://www.imageUrl123.com/"
                    )
                )
            )
        )
    )
)