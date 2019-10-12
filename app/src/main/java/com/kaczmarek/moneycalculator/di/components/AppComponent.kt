package com.kaczmarek.moneycalculator.di.components

import android.content.Context
import com.kaczmarek.moneycalculator.di.modules.AppModule
import com.kaczmarek.moneycalculator.di.modules.CalculatorModule
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

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }
}
