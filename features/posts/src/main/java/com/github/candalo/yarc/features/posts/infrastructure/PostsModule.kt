package com.github.candalo.yarc.features.posts.infrastructure

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import retrofit2.Retrofit

@Module
@InstallIn(ViewModelComponent::class)
internal object PostsModule {
    @Provides
    fun provideService(retrofit: Retrofit): PostsService = retrofit.create(PostsService::class.java)
}