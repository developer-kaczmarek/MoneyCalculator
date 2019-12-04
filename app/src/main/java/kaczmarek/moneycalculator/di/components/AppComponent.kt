package kaczmarek.moneycalculator.di.components

import android.content.Context
import kaczmarek.moneycalculator.di.components.CalculatorSubcomponent
import kaczmarek.moneycalculator.di.components.HistorySubcomponent
import kaczmarek.moneycalculator.di.components.MainSubcomponent
import kaczmarek.moneycalculator.di.components.SettingsSubcomponent
import kaczmarek.moneycalculator.di.modules.*
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    val context: Context

    fun calculator(calculatorModule: CalculatorModule): CalculatorSubcomponent

    fun history(historyModule: HistoryModule): HistorySubcomponent

    fun settings(settingsModule: SettingsModule): SettingsSubcomponent

    fun main(mainModule: MainModule): MainSubcomponent

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}
