package com.kaczmarek.moneycalculator.di.modules

import com.kaczmarek.moneycalculator.di.services.SettingsService
import com.kaczmarek.moneycalculator.di.scopes.SettingsScope
import com.kaczmarek.moneycalculator.di.services.database.DatabaseService
import com.kaczmarek.moneycalculator.ui.settings.InteractorSettings
import com.kaczmarek.moneycalculator.ui.settings.IRepositorySettings
import com.kaczmarek.moneycalculator.ui.settings.RepositorySettings
import dagger.Module
import dagger.Provides

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
@Module
class SettingsModule {
    @Provides
    @SettingsScope
    internal fun provideInteractor(repo: IRepositorySettings): InteractorSettings {
        return InteractorSettings(repo)
    }

    @Provides
    @SettingsScope
    internal fun provideRepository(
        databaseService: DatabaseService,
        settingsService: SettingsService
    ): IRepositorySettings {
        return RepositorySettings(
            databaseService,
            settingsService
        )
    }
}