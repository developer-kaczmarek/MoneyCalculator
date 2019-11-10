package com.kaczmarek.moneycalculator.di.modules

import com.kaczmarek.moneycalculator.di.scopes.MainScope
import com.kaczmarek.moneycalculator.di.services.SettingsService
import com.kaczmarek.moneycalculator.di.services.database.DatabaseService
import com.kaczmarek.moneycalculator.ui.main.interactors.MainInteractor
import com.kaczmarek.moneycalculator.ui.main.repositories.IMainRepository
import com.kaczmarek.moneycalculator.ui.main.repositories.MainRepository
import dagger.Module
import dagger.Provides

/**
 * Created by Angelina Podbolotova on 10.11.2019.
 */
@Module
class MainModule {
    @Provides
    @MainScope
    internal fun provideInteractor(repo: IMainRepository): MainInteractor {
        return MainInteractor(repo)
    }

    @Provides
    @MainScope
    internal fun provideRepository(databaseService: DatabaseService, settingsService: SettingsService): IMainRepository {
        return MainRepository(databaseService, settingsService)
    }
}