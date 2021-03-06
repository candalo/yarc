package github.com.candalo.yarc.infrastructure.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import github.com.candalo.yarc.infrastructure.api.Endpoints
import github.com.candalo.yarc.infrastructure.model.PostResponse
import retrofit2.HttpException
import java.io.IOException

internal class PostPagingSource(
    private val endpoints: Endpoints
) : PagingSource<String, PostResponse>() {
    override fun getRefreshKey(state: PagingState<String, PostResponse>): String? =
        state.anchorPosition?.let { state.closestItemToPosition(it)?.data?.id }

    override suspend fun load(
        params: LoadParams<String>
    ): LoadResult<String, PostResponse> =
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