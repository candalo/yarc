package com.github.candalo.yarc.infrastructure

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.github.candalo.yarc.domain.model.Post
import com.github.candalo.yarc.domain.model.PostComment
import com.github.candalo.yarc.domain.model.TreeNode
import com.github.candalo.yarc.infrastructure.api.Endpoints
import com.github.candalo.yarc.infrastructure.datasource.PostPagingSource
import com.github.candalo.yarc.infrastructure.mapper.Mapper
import com.github.candalo.yarc.infrastructure.mapper.PostCommentMapper
import com.github.candalo.yarc.infrastructure.mapper.PostMapper
import com.github.candalo.yarc.infrastructure.model.PostCommentDataResponse
import com.github.candalo.yarc.infrastructure.model.PostResponse
import com.github.candalo.yarc.infrastructure.repository.PostCommentRepository
import com.github.candalo.yarc.infrastructure.repository.PostRepository
import com.github.candalo.yarc.infrastructure.sanitizer.PostCommentEndpointSanitizer
import com.github.candalo.yarc.infrastructure.sanitizer.PostImageSanitizer
import com.github.candalo.yarc.infrastructure.sanitizer.Sanitizer
import com.github.candalo.yarc.presentation.PostDetailsViewModel
import com.github.candalo.yarc.presentation.PostsViewModel
import com.github.candalo.yarc.presentation.adapter.PostsAdapter
import com.github.candalo.yarc.presentation.adapter.PostsLoadStateAdapter
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

@OptIn(ExperimentalSerializationApi::class)
internal val infrastructure = module {
    single {
        OkHttpClient
            .Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl("https://www.reddit.com/")
            .addConverterFactory(
                Json {
                    ignoreUnknownKeys = true
                }.asConverterFactory(
                    "application/json".toMediaType()
                )
            )
            .client(get())
            .build()
            .create(Endpoints::class.java)
    }
    factory {
        PostPagingSource(get())
    }
    factory {
        Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = { get<PostPagingSource>() }
        ).flow
    }
    factory<Sanitizer<String>>(
        qualifier = named(PostImageSanitizer::class.java.simpleName)
    ) {
        PostImageSanitizer()
    }
    factory<Mapper<PostResponse, Post>>(
        qualifier = named(PostMapper::class.java.simpleName)
    ) {
        PostMapper(
            context = get(),
            imageSanitizer = get(qualifier = named(PostImageSanitizer::class.java.simpleName))
        )
    }
    factory {
        PostRepository(
            datasource = get(),
            mapper = get(qualifier = named(PostMapper::class.java.simpleName))
        )
    }
    factory<Mapper<PostCommentDataResponse, TreeNode<PostComment>>>(
        qualifier = named(PostCommentMapper::class.java.simpleName)
    ) {
        PostCommentMapper(
            context = get()
        )
    }
    factory<Sanitizer<String>>(
        qualifier = named(PostCommentEndpointSanitizer::class.java.simpleName)
    ) {
        PostCommentEndpointSanitizer()
    }
    factory {
        PostCommentRepository(
            endpoints = get(),
            endpointSanitizer = get(named(PostCommentEndpointSanitizer::class.java.simpleName)),
            mapper = get(qualifier = named(PostCommentMapper::class.java.simpleName))
        )
    }
}

internal val presentation = module {
    factory { (onPostSelected: (Post) -> Unit) ->
        PostsAdapter(onPostSelected)
    }
    factory {
        PostsLoadStateAdapter()
    }
    viewModel {
        PostsViewModel(
            repository = get()
        )
    }
    viewModel {
        PostDetailsViewModel(
            repository = get()
        )
    }
}