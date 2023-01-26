package com.github.candalo.yarc.features.posts.infrastructure.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.github.candalo.yarc.features.posts.infrastructure.PostsService
import com.github.candalo.yarc.features.posts.infrastructure.model.PostResponse
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import retrofit2.HttpException
import java.io.IOException

internal class PostsPagingSource @AssistedInject constructor(
    private val service: PostsService,
    @Assisted private val subreddit: String
) : PagingSource<String, PostResponse>() {
    override fun getRefreshKey(state: PagingState<String, PostResponse>): String? =
        state.anchorPosition?.let { state.closestItemToPosition(it)?.data?.id }

    override suspend fun load(
        params: LoadParams<String>
    ): LoadResult<String, PostResponse> =
        try {
            val response = service.getPosts(
                subreddit,
                params.key.orEmpty(),
                params.loadSize
            )

            LoadResult.Page(
                data = response.data.posts,
                prevKey = response.data.before,
                nextKey = response.data.after
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
}