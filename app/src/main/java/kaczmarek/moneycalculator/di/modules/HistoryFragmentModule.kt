package kaczmarek.moneycalculator.di.modules

import kaczmarek.moneycalculator.di.scopes.HistoryFragmentScope
import dagger.Module
import dagger.Provides
import kaczmarek.moneycalculator.data.session.port.SessionRepository
import kaczmarek.moneycalculator.domain.session.usecase.DeleteSessionByModelUseCase
import kaczmarek.moneycalculator.domain.session.usecase.GetSessionsListUseCase


/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
@Module
class HistoryFragmentModule {

    @Provides
    @HistoryFragmentScope
    fun provideDeleteSessionByModelUseCase(repository: SessionRepository): DeleteSessionByModelUseCase {
        return DeleteSessionByModelUseCase(repository)
    }

    @Provides
    @HistoryFragmentScope
    fun provideGetSessionUseCase(repository: SessionRepository): GetSessionsListUseCase {
        return GetSessionsListUseCase(repository)
    }

}