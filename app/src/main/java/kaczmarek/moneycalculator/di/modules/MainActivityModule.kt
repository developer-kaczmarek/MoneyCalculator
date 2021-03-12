package kaczmarek.moneycalculator.di.modules

import dagger.Module
import dagger.Provides
import kaczmarek.moneycalculator.data.session.SessionRepository
import kaczmarek.moneycalculator.data.settings.SettingsRepository
import kaczmarek.moneycalculator.di.scopes.MainActivityScope
import kaczmarek.moneycalculator.domain.session.usecase.DeleteSessionsByDateUseCase
import kaczmarek.moneycalculator.domain.settings.usecase.GetHistoryStoragePeriodUseCase

/**
 * Created by Angelina Podbolotova on 10.11.2019.
 */
@Module
class MainActivityModule {
    @Provides
    @MainActivityScope
    fun provideDeleteSessionUseCase(repository: SessionRepository): DeleteSessionsByDateUseCase {
        return DeleteSessionsByDateUseCase(repository)
    }

    @Provides
    @MainActivityScope
    fun provideGetHistoryStoragePeriodUseCase(repository: SettingsRepository): GetHistoryStoragePeriodUseCase {
        return GetHistoryStoragePeriodUseCase(repository)
    }
}