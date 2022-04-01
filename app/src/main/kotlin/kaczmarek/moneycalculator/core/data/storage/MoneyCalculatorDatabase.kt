package kaczmarek.moneycalculator.core.data.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kaczmarek.moneycalculator.core.data.storage.banknotes.BanknoteDbModel
import kaczmarek.moneycalculator.core.data.storage.banknotes.BanknotesDao

@Database(
    entities = [BanknoteDbModel::class],
    version = 1,
    exportSchema = false
)
abstract class MoneyCalculatorDatabase : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "calculator_db"

        fun create(context: Context) = Room
            .databaseBuilder(context, MoneyCalculatorDatabase::class.java, DATABASE_NAME)
            .createFromAsset("databases/initial_banknotes.db")
            .build()
    }

    abstract fun getBanknotesDao(): BanknotesDao
}