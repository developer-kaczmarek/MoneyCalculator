package kaczmarek.moneycalculator.di.modules

import dagger.Module
import dagger.Provides
import kaczmarek.moneycalculator.data.banknote.port.BanknoteRepository
import kaczmarek.moneycalculator.data.settings.port.SettingsRepository
import kaczmarek.moneycalculator.di.scopes.SettingsFragmentScope
import kaczmarek.moneycalculator.domain.banknote.usecase.GetBanknotesListUseCase
import kaczmarek.moneycalculator.domain.banknote.usecase.UpdateVisibilityBanknoteUseCase
import kaczmarek.moneycalculator.domain.settings.usecase.*

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
@Module
class SettingsFragmentModule {

    @Provides
    @SettingsFragmentScope
    fun provideGetHistoryStoragePeriodUseCase(repository: SettingsRepository): GetHistoryStoragePeriodUseCase {
        return GetHistoryStoragePeriodUseCase(repository)
    }

    @Provides
    @SettingsFragmentScope
    fun provideGetKeyboardLayoutUseCase(repository: SettingsRepository): GetKeyboardLayoutUseCase {
        return GetKeyboardLayoutUseCase(repository)
    }

    @Provides
    @SettingsFragmentScope
    fun provideGetAlwaysBacklightOnUseCase(repository: SettingsRepository): GetAlwaysBacklightOnUseCase {
        return GetAlwaysBacklightOnUseCase(repository)
    }

    @Provides
    @SettingsFragmentScope
    fun provideGetCountMeetComponentUseCase(repository: SettingsRepository): GetCountMeetComponentUseCase {
        return GetCountMeetComponentUseCase(repository)
    }

    @Provides
    @SettingsFragmentScope
    fun provideSetHistoryStoragePeriodUseCase(repository: SettingsRepository): SetHistoryStoragePeriodUseCase {
        return SetHistoryStoragePeriodUseCase(repository)
    }

    @Provides
    @SettingsFragmentScope
    fun provideSetKeyboardLayoutUseCase(repository: SettingsRepository): SetKeyboardLayoutUseCase {
        return SetKeyboardLayoutUseCase(repository)
    }

    @Provides
    @SettingsFragmentScope
    fun provideSetAlwaysBacklightOnUseCase(repository: SettingsRepository): SetAlwaysBacklightOnUseCase {
        return SetAlwaysBacklightOnUseCase(repository)
    }

    @Provides
    @SettingsFragmentScope
    fun provideUpdateCountMeetComponentUseCase(repository: SettingsRepository): UpdateCountMeetComponentUseCase {
        return UpdateCountMeetComponentUseCase(repository)
    }

    @Provides
    @SettingsFragmentScope
    fun provideGetBanknoteUseCase(repository: BanknoteRepository): GetBanknotesListUseCase {
        return GetBanknotesListUseCase(repository)
    }

    @Provides
    @SettingsFragmentScope
    fun provideUpdateVisibilityBanknoteUseCase(repository: BanknoteRepository): UpdateVisibilityBanknoteUseCase {
        return UpdateVisibilityBanknoteUseCase(repository)
    }

}