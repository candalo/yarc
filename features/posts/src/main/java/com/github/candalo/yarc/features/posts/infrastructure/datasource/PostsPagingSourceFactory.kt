package com.github.candalo.yarc.features.posts.infrastructure.datasource

import dagger.assisted.AssistedFactory

@AssistedFactory
internal interface PostsPagingSourceFactory {
    fun create(subreddit: String): PostsPagingSource
}