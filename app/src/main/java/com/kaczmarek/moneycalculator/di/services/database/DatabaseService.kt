package com.kaczmarek.moneycalculator.di.services.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kaczmarek.moneycalculator.di.services.database.models.Banknote

/**
 * Created by Angelina Podbolotova on 04.10.2019.
 */
@Database(entities = [Banknote::class], version = 1, exportSchema = false)
abstract class DatabaseService : RoomDatabase() {
    abstract fun banknoteDao(): BanknoteDao
}