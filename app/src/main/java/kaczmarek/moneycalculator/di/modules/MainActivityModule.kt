package kaczmarek.moneycalculator.di.modules

import dagger.Module
import dagger.Provides
import kaczmarek.moneycalculator.data.session.port.SessionRepository
import kaczmarek.moneycalculator.data.settings.port.SettingsRepository
import kaczmarek.moneycalculator.di.scopes.MainActivityScope
import kaczmarek.moneycalculator.domain.session.usecase.DeleteSessionUseCase
import kaczmarek.moneycalculator.domain.settings.usecase.GetHistoryStoragePeriodUseCase

/**
 * Created by Angelina Podbolotova on 10.11.2019.
 */
@Module
class MainActivityModule {

    @Provides
    @MainActivityScope
    fun provideDeleteSessionUseCase(repository: SessionRepository): DeleteSessionUseCase {
        return DeleteSessionUseCase(repository)
    }

    @Provides
    @MainActivityScope
    fun provideGetHistoryStoragePeriodUseCase(repository: SettingsRepository): GetHistoryStoragePeriodUseCase {
        return GetHistoryStoragePeriodUseCase(repository)
    }

}