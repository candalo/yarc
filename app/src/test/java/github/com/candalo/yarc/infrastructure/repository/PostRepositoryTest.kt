package github.com.candalo.yarc.infrastructure.repository

import androidx.paging.PagingData
import com.google.common.truth.Truth.assertThat
import github.com.candalo.yarc.domain.model.Post
import github.com.candalo.yarc.infrastructure.mapper.Mapper
import github.com.candalo.yarc.infrastructure.model.PostResponse
import github.com.candalo.yarc.infrastructure.post
import github.com.candalo.yarc.infrastructure.postResponse
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test

internal class PostRepositoryTest {
    private val pagingData = PagingData.from(listOf(postResponse))
    private val datasource = flowOf(pagingData)
    private val mapper = mockk<Mapper<PostResponse, Post>>()

    @Test
    fun `fetchPosts should get paging data with post response and map it to paging data with post`() = runBlockingTest {
        every { mapper.map(postResponse) } returns post

        val repository = PostRepository(datasource, mapper)

        repository.fetchPosts().collect {
            assertThat(it).isNotNull()
        }
    }
}