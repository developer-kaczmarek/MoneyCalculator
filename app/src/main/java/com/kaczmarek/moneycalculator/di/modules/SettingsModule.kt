package com.kaczmarek.moneycalculator.di.modules

import com.kaczmarek.moneycalculator.di.SettingsService
import com.kaczmarek.moneycalculator.di.scopes.SettingsScope
import com.kaczmarek.moneycalculator.di.services.database.DatabaseService
import com.kaczmarek.moneycalculator.ui.settings.interactors.SettingsInteractor
import com.kaczmarek.moneycalculator.ui.settings.repositories.ISettingsRepository
import com.kaczmarek.moneycalculator.ui.settings.repositories.SettingsRepository
import dagger.Module
import dagger.Provides

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
@Module
class SettingsModule {
    @Provides
    @SettingsScope
    internal fun provideInteractor(repo: ISettingsRepository): SettingsInteractor {
        return SettingsInteractor(repo)
    }

    @Provides
    @SettingsScope
    internal fun provideRepository(databaseService: DatabaseService, settingsService: SettingsService): ISettingsRepository {
        return SettingsRepository(databaseService, settingsService)
    }
}