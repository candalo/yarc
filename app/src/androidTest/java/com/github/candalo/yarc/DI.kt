package com.github.candalo.yarc

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.github.candalo.yarc.infrastructure.api.Endpoints
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockWebServer
import org.koin.dsl.module
import retrofit2.Retrofit

internal val testInfrastructure = module(override = true) {
    single {
        MockWebServer()
    }

    single {
        Retrofit.Builder()
            .baseUrl(get<MockWebServer>().url("").toString())
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
}