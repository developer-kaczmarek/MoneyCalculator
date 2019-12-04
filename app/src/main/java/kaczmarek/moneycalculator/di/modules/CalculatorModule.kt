package kaczmarek.moneycalculator.di.modules

import kaczmarek.moneycalculator.di.services.SettingsService
import kaczmarek.moneycalculator.di.scopes.CalculatorScope
import kaczmarek.moneycalculator.di.services.CalculatorService
import kaczmarek.moneycalculator.di.services.database.DatabaseService
import kaczmarek.moneycalculator.ui.calculator.InteractorCalculator
import kaczmarek.moneycalculator.ui.calculator.RepositoryCalculator
import kaczmarek.moneycalculator.ui.calculator.IRepositoryCalculator
import dagger.Module
import dagger.Provides

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
@Module
class CalculatorModule {
    @Provides
    @CalculatorScope
    internal fun provideInteractor(repo: IRepositoryCalculator): InteractorCalculator {
        return InteractorCalculator(
            repo
        )
    }

    @Provides
    @CalculatorScope
    internal fun provideRepository(
        databaseService: DatabaseService,
        settingsService: SettingsService,
        calculatorService: CalculatorService
    ): IRepositoryCalculator {
        return RepositoryCalculator(
            databaseService,
            settingsService,
            calculatorService
        )
    }
}