package github.com.candalo.hashmobilechallenge.infrastructure.api

import github.com.candalo.hashmobilechallenge.infrastructure.model.PostDetailsResponse
import github.com.candalo.hashmobilechallenge.infrastructure.model.SubRedditPostsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

internal interface Endpoints {
    @GET("r/androiddev.json")
    suspend fun getPosts(
        @Query("after") after: String,
        @Query("limit") limit: Int = 10
    ): SubRedditPostsResponse

    @GET("{commentsEndpoint}")
    suspend fun getComments(@Path("commentsEndpoint") endpoint: String): PostDetailsResponse
}