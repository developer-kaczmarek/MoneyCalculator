package com.kaczmarek.moneycalculator.di.modules

import android.content.Context
import android.content.SharedPreferences
import com.kaczmarek.moneycalculator.di.services.CalculatorService
import com.kaczmarek.moneycalculator.di.services.SettingsService
import com.kaczmarek.moneycalculator.di.services.SettingsService.Companion.KEY_SETTINGS
import com.kaczmarek.moneycalculator.di.services.database.DatabaseService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideDatabaseService(context: Context): DatabaseService {
        return DatabaseService.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideSharedPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(KEY_SETTINGS, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSettingsService(prefs: SharedPreferences) =
        SettingsService(prefs)

    @Provides
    @Singleton
    fun provideCalculatorService() = CalculatorService()
}