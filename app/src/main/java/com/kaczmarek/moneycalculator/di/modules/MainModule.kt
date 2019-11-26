package com.kaczmarek.moneycalculator.di.modules

import com.kaczmarek.moneycalculator.di.scopes.MainScope
import com.kaczmarek.moneycalculator.di.services.SettingsService
import com.kaczmarek.moneycalculator.di.services.database.DatabaseService
import com.kaczmarek.moneycalculator.ui.main.InteractorMain
import com.kaczmarek.moneycalculator.ui.main.IRepositoryMain
import com.kaczmarek.moneycalculator.ui.main.RepositoryMain
import dagger.Module
import dagger.Provides

/**
 * Created by Angelina Podbolotova on 10.11.2019.
 */
@Module
class MainModule {
    @Provides
    @MainScope
    internal fun provideInteractor(repo: IRepositoryMain): InteractorMain {
        return InteractorMain(repo)
    }

    @Provides
    @MainScope
    internal fun provideRepository(
        databaseService: DatabaseService,
        settingsService: SettingsService
    ): IRepositoryMain {
        return RepositoryMain(
            databaseService,
            settingsService
        )
    }
}