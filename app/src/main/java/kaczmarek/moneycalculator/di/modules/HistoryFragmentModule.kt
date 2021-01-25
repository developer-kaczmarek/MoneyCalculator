package kaczmarek.moneycalculator.di.modules

import kaczmarek.moneycalculator.di.scopes.HistoryFragmentScope
import dagger.Module
import dagger.Provides
import kaczmarek.moneycalculator.data.session.port.SessionRepository
import kaczmarek.moneycalculator.domain.session.usecase.DeleteSessionUseCase
import kaczmarek.moneycalculator.domain.session.usecase.GetSessionUseCase


/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
@Module
class HistoryFragmentModule {

    @Provides
    @HistoryFragmentScope
    fun provideDeleteSessionUseCase(repository: SessionRepository): DeleteSessionUseCase {
        return DeleteSessionUseCase(repository)
    }

    @Provides
    @HistoryFragmentScope
    fun provideGetSessionUseCase(repository: SessionRepository): GetSessionUseCase {
        return GetSessionUseCase(repository)
    }

}