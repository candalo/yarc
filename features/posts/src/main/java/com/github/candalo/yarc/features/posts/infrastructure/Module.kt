package com.github.candalo.yarc.features.posts.infrastructure

import androidx.paging.PagingConfig
import com.github.candalo.yarc.converter.Mapper
import com.github.candalo.yarc.converter.Sanitizer
import com.github.candalo.yarc.features.posts.domain.model.Post
import com.github.candalo.yarc.features.posts.domain.model.PostComment
import com.github.candalo.yarc.features.posts.domain.model.TreeNode
import com.github.candalo.yarc.features.posts.infrastructure.mapper.PostCommentMapper
import com.github.candalo.yarc.features.posts.infrastructure.mapper.PostMapper
import com.github.candalo.yarc.features.posts.infrastructure.model.PostCommentDataResponse
import com.github.candalo.yarc.features.posts.infrastructure.model.PostResponse
import com.github.candalo.yarc.features.posts.infrastructure.sanitizer.PostCommentEndpointSanitizer
import com.github.candalo.yarc.features.posts.infrastructure.sanitizer.PostImageSanitizer
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit
import javax.inject.Qualifier

@Module
@InstallIn(ViewModelComponent::class)
internal object ServiceModule {
    @Provides
    fun provideService(retrofit: Retrofit): PostsService = retrofit.create(PostsService::class.java)

    @Provides
    fun providePagingConfig(): PagingConfig = PagingConfig(pageSize = 10)
}

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class MapperModule {
    @Binds
    abstract fun bindPostMapper(
        postMapper: PostMapper
    ): Mapper<PostResponse, Post>

    @Binds
    abstract fun bindPostCommentsMapper(
        postCommentsMapper: PostCommentMapper
    ): Mapper<PostCommentDataResponse, TreeNode<PostComment>>
}

@Module
@InstallIn(ViewModelComponent::class)
internal abstract class SanitizerModule {
    @Binds
    @PostImageSanitizerType
    abstract fun bindPostImageSanitizer(
        postsSanitizer: PostImageSanitizer
    ): Sanitizer<String>

    @Binds
    @PostCommentEndpointSanitizerType
    abstract fun bindPostCommentEndpointSanitizer(
        postCommentEndpointSanitizer: PostCommentEndpointSanitizer
    ): Sanitizer<String>
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
internal annotation class PostImageSanitizerType

@Qualifier
@Retention(AnnotationRetention.BINARY)
internal annotation class PostCommentEndpointSanitizerType