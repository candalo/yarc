package github.com.candalo.hashmobilechallenge.infrastructure

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import github.com.candalo.hashmobilechallenge.domain.model.Post
import github.com.candalo.hashmobilechallenge.domain.model.PostComment
import github.com.candalo.hashmobilechallenge.domain.model.TreeNode
import github.com.candalo.hashmobilechallenge.infrastructure.api.Endpoints
import github.com.candalo.hashmobilechallenge.infrastructure.datasource.PostPagingSource
import github.com.candalo.hashmobilechallenge.infrastructure.mapper.Mapper
import github.com.candalo.hashmobilechallenge.infrastructure.mapper.PostCommentMapper
import github.com.candalo.hashmobilechallenge.infrastructure.mapper.PostMapper
import github.com.candalo.hashmobilechallenge.infrastructure.model.PostCommentDataResponse
import github.com.candalo.hashmobilechallenge.infrastructure.model.PostResponse
import github.com.candalo.hashmobilechallenge.infrastructure.repository.PostCommentRepository
import github.com.candalo.hashmobilechallenge.infrastructure.repository.PostRepository
import github.com.candalo.hashmobilechallenge.infrastructure.sanitizer.PostCommentEndpointSanitizer
import github.com.candalo.hashmobilechallenge.infrastructure.sanitizer.PostImageSanitizer
import github.com.candalo.hashmobilechallenge.infrastructure.sanitizer.Sanitizer
import github.com.candalo.hashmobilechallenge.presentation.PostDetailsViewModel
import github.com.candalo.hashmobilechallenge.presentation.PostsViewModel
import github.com.candalo.hashmobilechallenge.presentation.adapter.PostsAdapter
import github.com.candalo.hashmobilechallenge.presentation.adapter.PostsLoadStateAdapter
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

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
                    MediaType.get("application/json")
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
        PostCommentMapper()
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