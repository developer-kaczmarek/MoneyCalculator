package kaczmarek.moneycalculator.database.room.db

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Database
import androidx.room.Room
import androidx.room.TypeConverters
import kaczmarek.moneycalculator.database.room.BanknoteConverter
import kaczmarek.moneycalculator.database.room.dao.BanknoteDao
import kaczmarek.moneycalculator.database.room.dao.SessionDao
import kaczmarek.moneycalculator.database.room.model.BanknoteDBModel
import kaczmarek.moneycalculator.database.room.model.SessionDBModel

@Database(
    entities = [BanknoteDBModel::class, SessionDBModel::class],
    version = 2
)
@TypeConverters(BanknoteConverter::class)
abstract class RoomDatabase : androidx.room.RoomDatabase() {

    abstract fun banknoteDao(): BanknoteDao
    abstract fun sessionDao(): SessionDao

    companion object {
        @Volatile
        private var INSTANCE: RoomDatabase? = null

        private const val DB_NAME = "calculator_db"

        private lateinit var prefs: SharedPreferences

        fun getDatabase(context: Context, prefs: SharedPreferences): RoomDatabase {
            Companion.prefs = prefs
            INSTANCE?.let { return it }

            synchronized(this) {
                val instance = Room.databaseBuilder(context, RoomDatabase::class.java, DB_NAME)
                    .createFromAsset("databases/$DB_NAME.db")
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}