package kaczmarek.moneycalculator.database.room.db

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Database
import androidx.room.Room
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
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

        private val migrationFrom1To2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE TABLE banknotes_backup(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name REAL NOT NULL, count INTEGER NOT NULL, amount REAL NOT NULL, backgroundColor TEXT NOT NULL, isShow INTEGER NOT NULL)")
                database.execSQL("INSERT INTO banknotes_backup SELECT id, name, count, amount, backgroundColor, isShow FROM banknotes")
                database.execSQL("DROP TABLE banknotes")
                database.execSQL("CREATE TABLE banknotes(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name REAL NOT NULL, count INTEGER NOT NULL, amount REAL NOT NULL, backgroundColor TEXT NOT NULL, isShow INTEGER NOT NULL)")
                database.execSQL("INSERT INTO banknotes SELECT id, name, count, amount, backgroundColor, isShow FROM banknotes_backup")
                database.execSQL("DROP TABLE banknotes_backup")
            }
        }

        fun getDatabase(context: Context, prefs: SharedPreferences): RoomDatabase {
            Companion.prefs = prefs
            INSTANCE?.let { return it }

            synchronized(this) {
                val instance = Room.databaseBuilder(context, RoomDatabase::class.java, DB_NAME)
                    .createFromAsset("databases/$DB_NAME.db")
                    .addMigrations(migrationFrom1To2)
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}