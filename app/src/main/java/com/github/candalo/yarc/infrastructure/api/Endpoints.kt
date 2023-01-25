package com.github.candalo.yarc.infrastructure.api

import com.github.candalo.yarc.infrastructure.model.PostDetailsResponse
import com.github.candalo.yarc.infrastructure.model.PostsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface Endpoints {
    @GET("r/androiddev.json")
    suspend fun getPosts(
        @Query("after") after: String,
        @Query("limit") limit: Int = 10
    ): PostsResponse

    @GET("{commentsEndpoint}")
    suspend fun getComments(
        @Path("commentsEndpoint") endpoint: String
    ): List<PostDetailsResponse>
}