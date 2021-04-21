package github.com.candalo.hashmobilechallenge

import android.app.Application
import github.com.candalo.hashmobilechallenge.infrastructure.infrastructure
import github.com.candalo.hashmobilechallenge.infrastructure.presentation
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

internal class HashMobileChallengeApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        configureKoin()
    }

    private fun configureKoin() {
        startKoin {
            androidLogger()
            androidContext(this@HashMobileChallengeApplication)
            modules(infrastructure, presentation)
        }
    }
}