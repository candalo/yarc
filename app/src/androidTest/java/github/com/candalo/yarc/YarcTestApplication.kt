package github.com.candalo.yarc

import github.com.candalo.yarc.infrastructure.infrastructure
import github.com.candalo.yarc.infrastructure.presentation
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
                testInfrastructure,
                presentation
            )
        }
    }
}