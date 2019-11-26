package com.kaczmarek.moneycalculator.di.modules

import com.kaczmarek.moneycalculator.di.scopes.HistoryScope
import com.kaczmarek.moneycalculator.di.services.database.DatabaseService
import com.kaczmarek.moneycalculator.ui.history.InteractorHistory
import com.kaczmarek.moneycalculator.ui.history.RepositoryHistory
import com.kaczmarek.moneycalculator.ui.history.IRepositoryHistory
import dagger.Module
import dagger.Provides

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
@Module
class HistoryModule {
    @Provides
    @HistoryScope
    internal fun provideInteractor(repo: IRepositoryHistory): InteractorHistory {
        return InteractorHistory(repo)
    }

    @Provides
    @HistoryScope
    internal fun provideRepository(databaseService: DatabaseService): IRepositoryHistory {
        return RepositoryHistory(
            databaseService
        )
    }
}