package com.github.candalo.yarc.features.posts.infrastructure

import com.github.candalo.yarc.features.posts.infrastructure.model.PostDetailsResponse
import com.github.candalo.yarc.features.posts.infrastructure.model.PostsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface PostsService {
    @GET("r/{subreddit}.json")
    suspend fun getPosts(
        @Path("subreddit") subreddit: String,
        @Query("after") after: String,
        @Query("limit") limit: Int
    ): PostsResponse

    @GET("{commentsEndpoint}")
    suspend fun getPostComments(
        @Path("commentsEndpoint") endpoint: String
    ): List<PostDetailsResponse>
}