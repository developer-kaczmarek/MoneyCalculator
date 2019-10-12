package com.kaczmarek.moneycalculator.di.modules

import android.content.Context
import com.kaczmarek.moneycalculator.di.services.database.DatabaseService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
@Module
object AppModule {
    @JvmStatic
    @Provides
    @Singleton
    fun provideDatabaseService(context: Context): DatabaseService {
        return DatabaseService.getDatabase(context)
    }
}