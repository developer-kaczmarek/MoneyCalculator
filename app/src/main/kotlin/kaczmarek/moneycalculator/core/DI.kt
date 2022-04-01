package kaczmarek.moneycalculator.core

import android.content.Context
import android.content.SharedPreferences
import kaczmarek.moneycalculator.core.data.storage.MoneyCalculatorDatabase
import kaczmarek.moneycalculator.core.data.storage.banknotes.BanknotesStorage
import kaczmarek.moneycalculator.core.data.storage.banknotes.BanknotesStorageImpl
import kaczmarek.moneycalculator.core.domain.ChangeBanknoteVisibilityInteractor
import kaczmarek.moneycalculator.core.domain.GetBanknotesInteractor
import kaczmarek.moneycalculator.core.ui.ActivityProvider
import kaczmarek.moneycalculator.core.ui.error_handling.ErrorHandler
import kaczmarek.moneycalculator.core.ui.external_app_service.ExternalAppService
import kaczmarek.moneycalculator.core.ui.external_app_service.ExternalAppServiceImpl
import kaczmarek.moneycalculator.core.ui.message.MessageService
import kaczmarek.moneycalculator.core.ui.message.MessageServiceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val coreModule = module {
    single { ActivityProvider() }
    single<MessageService> { MessageServiceImpl() }
    single { ErrorHandler(get()) }
    single<ExternalAppService> { ExternalAppServiceImpl(get()) }
    single { MoneyCalculatorDatabase.create(androidContext()) }
    single { get<MoneyCalculatorDatabase>().getBanknotesDao() }
    single<BanknotesStorage> { BanknotesStorageImpl(get()) }
    factory { ChangeBanknoteVisibilityInteractor(get()) }
    factory { GetBanknotesInteractor(get()) }
}

fun createPreferences(context: Context, prefsName: String): SharedPreferences {
    return context.getSharedPreferences(prefsName, Context.MODE_PRIVATE)
}