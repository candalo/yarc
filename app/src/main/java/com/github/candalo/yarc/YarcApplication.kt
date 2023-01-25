package com.github.candalo.yarc

import android.app.Application
import com.github.candalo.yarc.infrastructure.infrastructure
import com.github.candalo.yarc.infrastructure.presentation
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

internal open class YarcApplication : Application() {
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