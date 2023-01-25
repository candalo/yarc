package com.github.candalo.yarc

import com.github.candalo.yarc.infrastructure.infrastructure
import com.github.candalo.yarc.infrastructure.presentation
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

internal class YarcTestApplication : YarcApplication() {
    override fun onCreate() {
        configureKoin()
    }

    private fun configureKoin() {
        startKoin {
            androidLogger()
            androidContext(this@YarcTestApplication)
            modules(
                infrastructure,
                presentation
            )
        }
    }
}