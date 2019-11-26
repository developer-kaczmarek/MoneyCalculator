package com.kaczmarek.moneycalculator.di.modules

import com.kaczmarek.moneycalculator.di.services.SettingsService
import com.kaczmarek.moneycalculator.di.scopes.CalculatorScope
import com.kaczmarek.moneycalculator.di.services.CalculatorService
import com.kaczmarek.moneycalculator.di.services.database.DatabaseService
import com.kaczmarek.moneycalculator.ui.calculator.InteractorCalculator
import com.kaczmarek.moneycalculator.ui.calculator.RepositoryCalculator
import com.kaczmarek.moneycalculator.ui.calculator.IRepositoryCalculator
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