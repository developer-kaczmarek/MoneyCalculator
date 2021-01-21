package kaczmarek.moneycalculator.di.modules

import kaczmarek.moneycalculator.di.services.SettingsService
import kaczmarek.moneycalculator.di.scopes.SettingsScope
import kaczmarek.moneycalculator.di.services.database.DatabaseService
import kaczmarek.moneycalculator.ui.settings.SettingsInteractor
import kaczmarek.moneycalculator.ui.settings.ISettingsRepository
import kaczmarek.moneycalculator.ui.settings.SettingsRepository
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
    internal fun provideRepository(
        databaseService: DatabaseService,
        settingsService: SettingsService
    ): ISettingsRepository {
        return SettingsRepository(databaseService, settingsService)
    }
}