package com.github.candalo.yarc.features.posts.infrastructure

import androidx.paging.PagingConfig
import com.github.candalo.yarc.converter.Mapper
import com.github.candalo.yarc.converter.Sanitizer
import com.github.candalo.yarc.features.posts.domain.model.Post
import com.github.candalo.yarc.features.posts.infrastructure.mapper.PostMapper
import com.github.candalo.yarc.features.posts.infrastructure.model.PostResponse
import com.github.candalo.yarc.features.posts.infrastructure.sanitizer.PostImageSanitizer
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

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
    abstract fun bindMapper(
        postMapper: PostMapper
    ): Mapper<PostResponse, Post>

    @Binds
    abstract fun bindSanitizer(
        postsSanitizer: PostImageSanitizer
    ): Sanitizer<String>
}