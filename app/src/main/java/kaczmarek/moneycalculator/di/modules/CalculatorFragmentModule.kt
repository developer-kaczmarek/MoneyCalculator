package kaczmarek.moneycalculator.di.modules

import dagger.Module
import dagger.Provides
import kaczmarek.moneycalculator.data.session.port.SessionRepository
import kaczmarek.moneycalculator.data.settings.port.SettingsRepository
import kaczmarek.moneycalculator.di.scopes.CalculatorFragmentScope
import kaczmarek.moneycalculator.domain.session.usecase.SaveSessionUseCase
import kaczmarek.moneycalculator.domain.settings.usecase.GetAlwaysBacklightOnUseCase
import kaczmarek.moneycalculator.domain.settings.usecase.GetCountMeetComponentUseCase
import kaczmarek.moneycalculator.domain.settings.usecase.GetKeyboardLayoutUseCase

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
@Module
class CalculatorFragmentModule {

    @Provides
    @CalculatorFragmentScope
    fun provideGetKeyboardLayoutUseCase(repository: SettingsRepository): GetKeyboardLayoutUseCase {
        return GetKeyboardLayoutUseCase(repository)
    }

    @Provides
    @CalculatorFragmentScope
    fun provideGetAlwaysBacklightOnUseCase(repository: SettingsRepository): GetAlwaysBacklightOnUseCase {
        return GetAlwaysBacklightOnUseCase(repository)
    }

    @Provides
    @CalculatorFragmentScope
    fun provideGetCountMeetComponentUseCase(repository: SettingsRepository): GetCountMeetComponentUseCase {
        return GetCountMeetComponentUseCase(repository)
    }

    @Provides
    @CalculatorFragmentScope
    fun provideSaveSessionUseCase(repository: SessionRepository): SaveSessionUseCase {
        return SaveSessionUseCase(repository)
    }

}