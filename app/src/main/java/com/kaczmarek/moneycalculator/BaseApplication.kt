package com.kaczmarek.moneycalculator

import android.app.Application
import com.facebook.stetho.Stetho
import com.kaczmarek.moneycalculator.di.DIManager
import com.kaczmarek.moneycalculator.di.components.DaggerAppComponent

/**
 * Created by Angelina Podbolotova on 22.09.2019.
 */
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DIManager.appComponent = DaggerAppComponent.builder()
            .context(applicationContext)
            .build()

        Stetho.initializeWithDefaults(applicationContext)

    }
}