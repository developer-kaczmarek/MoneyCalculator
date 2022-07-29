package kaczmarek.moneycalculator

import android.app.Application
import kaczmarek.moneycalculator.core.ComponentFactory
import kaczmarek.moneycalculator.core.KoinProvider
import org.koin.android.ext.koin.androidContext
import org.koin.core.Koin
import org.koin.dsl.koinApplication

@Suppress("unused")
class App : Application(), KoinProvider {

    override lateinit var koin: Koin
        private set

    override fun onCreate() {
        super.onCreate()
        koin = createKoin()
        koin.declare(ComponentFactory(koin))
    }

    private fun createKoin(): Koin {
        return koinApplication {
            androidContext(this@App)
            modules(allModules)
        }.koin
    }
}