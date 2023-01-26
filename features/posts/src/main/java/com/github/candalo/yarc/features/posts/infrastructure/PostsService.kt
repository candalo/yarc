package com.github.candalo.yarc.features.posts.infrastructure

import com.github.candalo.yarc.features.posts.infrastructure.model.PostsResponse
import retrofit2.http.GET

internal interface PostsService {
    @GET("r/popular.json")
    suspend fun getPopularPosts(): PostsResponse

    @GET("r/all.json")
    suspend fun getAllPosts(): PostsResponse
}