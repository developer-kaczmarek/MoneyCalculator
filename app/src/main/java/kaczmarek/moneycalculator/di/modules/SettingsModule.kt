package kaczmarek.moneycalculator.di.modules

import dagger.Module
import dagger.Provides
import kaczmarek.moneycalculator.data.settings.port.SettingsRepository
import kaczmarek.moneycalculator.di.services.SettingsSharedPrefsService
import javax.inject.Singleton

@Module
object SettingsModule {

    @Provides
    @Singleton
    fun provideSettingsRepository(settingsService: SettingsSharedPrefsService): SettingsRepository {
        return SettingsRepository(settingsService)
    }
}