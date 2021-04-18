package github.com.candalo.hashmobilechallenge.infrastructure.api

import retrofit2.http.Query

internal interface Endpoints {
    suspend fun getPosts(
        @Query("after") after: String,
        @Query("limit") limit: Int = 10
    )
}