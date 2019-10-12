package com.kaczmarek.moneycalculator

import android.app.Application
import com.facebook.stetho.Stetho
import com.kaczmarek.moneycalculator.di.DIManager
import com.kaczmarek.moneycalculator.di.components.DaggerAppComponent
import com.ub.utils.UbUtils

/**
 * Created by Angelina Podbolotova on 22.09.2019.
 */
class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        DIManager.appComponent = DaggerAppComponent
            .builder()
            .context(this)
            .build()

        val initializerBuilder = Stetho.newInitializerBuilder(this)
        initializerBuilder.enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
        initializerBuilder.enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
        val initializer = initializerBuilder.build()
        Stetho.initialize(initializer)

        UbUtils.init(this)
    }
}