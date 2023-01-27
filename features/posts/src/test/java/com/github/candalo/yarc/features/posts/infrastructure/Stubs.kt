package com.github.candalo.yarc.features.posts.infrastructure

import com.github.candalo.yarc.features.posts.domain.model.Post
import com.github.candalo.yarc.features.posts.domain.model.PostComment
import com.github.candalo.yarc.features.posts.domain.model.PostMedia
import com.github.candalo.yarc.features.posts.infrastructure.model.*
import java.time.Instant

private val instant = Instant.now().minusSeconds(3600)

internal val postCommentDataResponse = PostCommentDataResponse(
    "Hello darkness my old friend",
    "candalo",
    100,
    instant,
    null
)

internal val postComment = PostComment(
    "Hello darkness my old friend",
    "candalo",
    100,
    "1h",
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

internal val postCommentReply = PostComment(
    "I've come to talk with you again",
    "oladnac",
    50,
    "1h",
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

internal val post = Post(
    "1",
    "Android Studio is buggy",
    "Android Studio is very very buggy",
    "candalo",
    1000,
    100,
    "1h",
    "permalink",
    PostMedia(null, null)
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

internal val postDetailsResponse = PostDetailsResponse(
    PostDetailsDataResponse(
        listOf(
            PostCommentResponse(
                postCommentDataResponse
            ),
            PostCommentResponse(
                postCommentDataResponseWithReplies
            )
        )
    )
)

internal const val postCommentRepliesJson = """
{
   "replies":{
      "kind":"Listing",
      "data":{
         "modhash":"",
         "dist":null,
         "children":[
            {
               "kind":"t1",
               "data":{
                  "total_awards_received":0,
                  "approved_at_utc":null,
                  "comment_type":null,
                  "awarders":[
                     
                  ],
                  "mod_reason_by":null,
                  "banned_by":null,
                  "ups":1,
                  "author_flair_type":"text",
                  "removal_reason":null,
                  "link_id":"t3_n36o42",
                  "author_flair_template_id":null,
                  "likes":null,
                  "replies":"",
                  "user_reports":[
                     
                  ],
                  "saved":false,
                  "id":"gwpcamm",
                  "banned_at_utc":null,
                  "mod_reason_title":null,
                  "gilded":0,
                  "archived":false,
                  "no_follow":true,
                  "author":"HedonicAthlete",
                  "can_mod_post":false,
                  "send_replies":true,
                  "parent_id":"t1_gwp6byp",
                  "score":1,
                  "author_fullname":"t2_8uoscafh",
                  "report_reasons":null,
                  "approved_by":null,
                  "all_awardings":[
                     
                  ],
                  "subreddit_id":"t5_2r26y",
                  "collapsed":false,
                  "body":"Can you point out the issues in the article for those of us who don’t know coroutines and want to learn it right?",
                  "edited":false,
                  "author_flair_css_class":null,
                  "is_submitter":false,
                  "downs":0,
                  "author_flair_richtext":[
                     
                  ],
                  "author_patreon_flair":false,
                  "body_html":"&lt;div class=\"md\"&gt;&lt;p&gt;Can you point out the issues in the article for those of us who don’t know coroutines and want to learn it right?&lt;/p&gt;\n&lt;/div&gt;",
                  "gildings":{
                     
                  },
                  "collapsed_reason":null,
                  "associated_award":null,
                  "stickied":false,
                  "author_premium":false,
                  "subreddit_type":"public",
                  "can_gild":true,
                  "top_awarded_type":null,
                  "author_flair_text_color":null,
                  "score_hidden":false,
                  "permalink":"/r/androiddev/comments/n36o42/coroutines_for_beginners_the_only_article_you/gwpcamm/",
                  "num_reports":null,
                  "locked":false,
                  "name":"t1_gwpcamm",
                  "created":1620014287,
                  "subreddit":"androiddev",
                  "author_flair_text":null,
                  "treatment_tags":[
                     
                  ],
                  "created_utc":1619985487,
                  "subreddit_name_prefixed":"r/androiddev",
                  "controversiality":0,
                  "depth":1,
                  "author_flair_background_color":null,
                  "collapsed_because_crowd_control":null,
                  "mod_reports":[
                     
                  ],
                  "mod_note":null,
                  "distinguished":null
               }
            }
         ],
         "after":null,
         "before":null
      }
   }
}
"""