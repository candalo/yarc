package github.com.candalo.hashmobilechallenge.infrastructure

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import github.com.candalo.hashmobilechallenge.infrastructure.api.Endpoints
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import org.koin.dsl.module
import retrofit2.Retrofit

internal val infrastructure = module {
    single {
        Retrofit.Builder()
            .baseUrl("https://www.reddit.com/")
            .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
            .build()
            .create(Endpoints::class.java)
    }
}