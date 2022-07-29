package kaczmarek.moneycalculator.core.storage

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kaczmarek.moneycalculator.core.banknote.data.BanknoteConverter
import kaczmarek.moneycalculator.core.banknote.data.BanknoteDbModel
import kaczmarek.moneycalculator.core.banknote.data.BanknotesDao
import kaczmarek.moneycalculator.sessions.data.SessionDbModel
import kaczmarek.moneycalculator.sessions.data.SessionsDao

@Database(
    entities = [BanknoteDbModel::class, SessionDbModel::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(BanknoteConverter::class)
abstract class MoneyCalculatorDatabase : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "calculator_db"

        fun create(context: Context) = Room
            .databaseBuilder(context, MoneyCalculatorDatabase::class.java, DATABASE_NAME)
            .createFromAsset("databases/initial_banknotes.db")
            .build()
    }

    abstract fun getBanknotesDao(): BanknotesDao

    abstract fun getSessionsDao(): SessionsDao
}