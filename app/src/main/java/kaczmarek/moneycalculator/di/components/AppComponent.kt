package kaczmarek.moneycalculator.di.components

import android.content.Context
import kaczmarek.moneycalculator.di.modules.*
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
@Singleton
@Component(modules = [AppModule::class, BanknoteModule::class, SettingsModule::class, SessionModule::class])
interface AppComponent {

    val context: Context

    fun calculator(calculatorModule: CalculatorFragmentModule): CalculatorSubcomponent

    fun history(historyModule: HistoryFragmentModule): HistorySubcomponent

    fun settings(settingsFragmentModule: SettingsFragmentModule): SettingsSubcomponent

    fun main(mainModule: MainActivityModule): MainSubcomponent

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

}
