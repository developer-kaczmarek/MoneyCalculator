package kaczmarek.moneycalculator.core

import android.content.Context
import android.content.SharedPreferences
import kaczmarek.moneycalculator.core.storage.MoneyCalculatorDatabase
import kaczmarek.moneycalculator.core.banknote.data.BanknotesStorage
import kaczmarek.moneycalculator.core.banknote.data.BanknotesStorageImpl
import kaczmarek.moneycalculator.core.banknote.domain.ChangeBanknoteVisibilityInteractor
import kaczmarek.moneycalculator.core.banknote.domain.GetBanknotesInteractor
import kaczmarek.moneycalculator.core.error_handling.ErrorHandler
import kaczmarek.moneycalculator.core.external_app_service.ExternalAppService
import kaczmarek.moneycalculator.core.external_app_service.ExternalAppServiceImpl
import kaczmarek.moneycalculator.core.message.data.MessageService
import kaczmarek.moneycalculator.core.message.data.MessageServiceImpl
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