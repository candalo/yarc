package com.github.candalo.yarc.features.posts.infrastructure.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.github.candalo.yarc.converter.Mapper
import com.github.candalo.yarc.features.posts.domain.model.Post
import com.github.candalo.yarc.features.posts.infrastructure.datasource.PostsPagingSource
import com.github.candalo.yarc.features.posts.infrastructure.datasource.PostsPagingSourceFactory
import com.github.candalo.yarc.features.posts.infrastructure.model.PostResponse
import com.github.candalo.yarc.features.posts.infrastructure.post
import com.github.candalo.yarc.features.posts.infrastructure.postResponse
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkClass
import io.mockk.mockkConstructor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test

@OptIn(ExperimentalCoroutinesApi::class)
internal class PostsRepositoryTest {
    private val pagingData = PagingData.from(listOf(postResponse))
    private val datasource = flowOf(pagingData)
    private val postsPagingSource = mockk<PostsPagingSource>()
    private val postsPagingSourceFactory = mockk<PostsPagingSourceFactory>()
    private val pagingConfig = mockk<PagingConfig>()
    private val mapper = mockk<Mapper<PostResponse, Post>>()

    @Test
    fun `fetchPosts should get paging data with post response and map it to paging data with post`() = runTest {
        val subreddit = "androiddev"

        mockkConstructor(Pager::class)
        every { anyConstructed<Pager<String, PostResponse>>().flow } returns datasource
        every { mapper.map(postResponse) } returns post

        val repository = PostsRepository(postsPagingSourceFactory, pagingConfig, mapper)

        repository.fetchPosts(subreddit).collect {
            assertThat(it).isNotNull()
        }
    }
}