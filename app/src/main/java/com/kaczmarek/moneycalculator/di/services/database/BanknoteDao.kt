package com.kaczmarek.moneycalculator.di.services.database

import androidx.room.*
import com.kaczmarek.moneycalculator.di.services.database.models.Banknote

/**
 * Created by Angelina Podbolotova on 04.10.2019.
 */
@Dao
interface BanknoteDao {
    @Query("SELECT * FROM banknotes")
    fun getAllBanknotes(): List<Banknote>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBanknote(banknote: Banknote)

    @Update
    fun updateBanknote(banknote: Banknote)
}
