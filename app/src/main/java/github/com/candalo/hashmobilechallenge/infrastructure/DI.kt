package github.com.candalo.hashmobilechallenge.infrastructure

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import github.com.candalo.hashmobilechallenge.infrastructure.api.Endpoints
import github.com.candalo.hashmobilechallenge.infrastructure.datasource.PostPagingSource
import github.com.candalo.hashmobilechallenge.infrastructure.mapper.PostMapper
import github.com.candalo.hashmobilechallenge.infrastructure.repository.PostRepository
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
    factory {
        PostPagingSource(get())
    }
    factory {
        Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory =  { get<PostPagingSource>() }
        ).flow
    }
    factory {
        PostMapper()
    }
    factory {
        PostRepository(
            datasource = get(),
            mapper = get()
        )
    }
}