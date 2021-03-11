package kaczmarek.moneycalculator.di.modules

import android.content.Context
import android.content.SharedPreferences
import kaczmarek.moneycalculator.di.services.TemporaryStorageService
import kaczmarek.moneycalculator.di.services.SettingsSharedPrefsService
import kaczmarek.moneycalculator.di.services.SettingsSharedPrefsService.Companion.KEY_SETTINGS
import dagger.Module
import dagger.Provides
import kaczmarek.moneycalculator.database.room.db.RoomDatabase
import javax.inject.Singleton

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(KEY_SETTINGS, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideSettingsService(prefs: SharedPreferences) = SettingsSharedPrefsService(prefs)

    @Provides
    @Singleton
    fun provideCalculatorService() = TemporaryStorageService()

    @Provides
    @Singleton
    fun provideRoomDatabase(context: Context, prefs: SharedPreferences): RoomDatabase {
        return RoomDatabase.getDatabase(context, prefs)
    }

}