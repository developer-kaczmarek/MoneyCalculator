package com.kaczmarek.moneycalculator.di.modules

import com.kaczmarek.moneycalculator.di.scopes.CalculatorScope
import com.kaczmarek.moneycalculator.di.services.database.DatabaseService
import com.kaczmarek.moneycalculator.ui.calculator.interactors.CalculatorInteractor
import com.kaczmarek.moneycalculator.ui.calculator.repositories.CalculatorRepository
import com.kaczmarek.moneycalculator.ui.calculator.repositories.ICalculatorRepository
import dagger.Module
import dagger.Provides

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
@Module
class CalculatorModule {
    @Provides
    @CalculatorScope
    internal fun provideInteractor(repo: ICalculatorRepository): CalculatorInteractor {
        return CalculatorInteractor(repo)
    }

    @Provides
    @CalculatorScope
    internal fun provideRepository(databaseService: DatabaseService): ICalculatorRepository {
        return CalculatorRepository(databaseService)
    }
}