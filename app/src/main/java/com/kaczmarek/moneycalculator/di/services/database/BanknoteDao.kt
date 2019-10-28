package com.kaczmarek.moneycalculator.di.services.database

import androidx.room.*
import com.kaczmarek.moneycalculator.di.services.database.models.Banknote

/**
 * Created by Angelina Podbolotova on 04.10.2019.
 */
@Dao
interface BanknoteDao {
    @Query("SELECT * FROM banknotes")
    fun getAll(): List<Banknote>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(banknotes: List<Banknote>)

    @Update
    fun updateBanknote(banknote: Banknote)

    @Query("SELECT COUNT(*) FROM banknotes")
    fun count(): Int

}
