package github.com.candalo.yarc

import android.app.Application
import github.com.candalo.yarc.infrastructure.infrastructure
import github.com.candalo.yarc.infrastructure.presentation
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

internal class YarcApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        configureKoin()
    }

    private fun configureKoin() {
        startKoin {
            androidLogger()
            androidContext(this@YarcApplication)
            modules(infrastructure, presentation)
        }
    }
}