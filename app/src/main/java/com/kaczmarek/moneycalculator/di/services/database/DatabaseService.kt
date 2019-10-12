package com.kaczmarek.moneycalculator.di.services.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kaczmarek.moneycalculator.di.services.database.models.Banknote
import com.kaczmarek.moneycalculator.di.services.database.models.Session

/**
 * Created by Angelina Podbolotova on 04.10.2019.
 */
@Database(entities = [Banknote::class, Session::class], version = 1, exportSchema = false)
@TypeConverters(BanknoteConverters::class)
abstract class DatabaseService : RoomDatabase() {

    abstract fun banknoteDao(): BanknoteDao

    abstract fun sessionDao(): SessionDao

    private fun populateInitialData(context: Context) {
        if (banknoteDao().count() == 0) {
            runInTransaction {
                val jsonData = context.assets.open(JSON_NAME)
                    .bufferedReader().use {
                        it.readText()
                    }
                val type = object : TypeToken<List<Banknote>>() {}.type
                val listBanknote: List<Banknote> = Gson().fromJson(jsonData, type)
                banknoteDao().insertAll(listBanknote)
            }
        }
    }


    companion object {

        private const val DB_NAME = "calculator_db"
        private const val JSON_NAME = "InitData.json"

        fun getDatabase(context: Context): DatabaseService {

            synchronized(this) {
                val instance = Room.databaseBuilder(context, DatabaseService::class.java, DB_NAME)
                    .allowMainThreadQueries().build()
                instance.populateInitialData(context)
                return instance
            }
        }
    }
}