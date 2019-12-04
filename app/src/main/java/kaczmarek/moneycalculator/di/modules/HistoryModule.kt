package kaczmarek.moneycalculator.di.modules

import kaczmarek.moneycalculator.di.scopes.HistoryScope
import kaczmarek.moneycalculator.di.services.database.DatabaseService
import kaczmarek.moneycalculator.ui.history.InteractorHistory
import kaczmarek.moneycalculator.ui.history.RepositoryHistory
import kaczmarek.moneycalculator.ui.history.IRepositoryHistory
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