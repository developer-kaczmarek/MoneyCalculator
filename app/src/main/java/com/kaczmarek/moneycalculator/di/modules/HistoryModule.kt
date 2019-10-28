package com.kaczmarek.moneycalculator.di.modules

import com.kaczmarek.moneycalculator.di.SettingsService
import com.kaczmarek.moneycalculator.di.scopes.HistoryScope
import com.kaczmarek.moneycalculator.di.services.database.DatabaseService
import com.kaczmarek.moneycalculator.ui.history.interactors.HistoryInteractor
import com.kaczmarek.moneycalculator.ui.history.repositories.HistoryRepository
import com.kaczmarek.moneycalculator.ui.history.repositories.IHistoryRepository
import dagger.Module
import dagger.Provides

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
@Module
class HistoryModule {
    @Provides
    @HistoryScope
    internal fun provideInteractor(repo: IHistoryRepository): HistoryInteractor {
        return HistoryInteractor(repo)
    }

    @Provides
    @HistoryScope
    internal fun provideRepository(databaseService: DatabaseService, settingsService: SettingsService): IHistoryRepository {
        return HistoryRepository(databaseService, settingsService)
    }
}