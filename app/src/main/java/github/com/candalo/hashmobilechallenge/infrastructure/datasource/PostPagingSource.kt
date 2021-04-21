package github.com.candalo.hashmobilechallenge.infrastructure.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import github.com.candalo.hashmobilechallenge.infrastructure.api.Endpoints
import github.com.candalo.hashmobilechallenge.infrastructure.model.SubRedditPostResponse
import retrofit2.HttpException
import java.io.IOException

internal class PostPagingSource(
        private val endpoints: Endpoints
) : PagingSource<String, SubRedditPostResponse>() {
    override fun getRefreshKey(state: PagingState<String, SubRedditPostResponse>): String? =
            state.anchorPosition?.let { state.closestItemToPosition(it)?.data?.id }

    override suspend fun load(
            params: LoadParams<String>
    ): LoadResult<String, SubRedditPostResponse> =
            try {
                val response = endpoints.getPosts(params.key ?: "")

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