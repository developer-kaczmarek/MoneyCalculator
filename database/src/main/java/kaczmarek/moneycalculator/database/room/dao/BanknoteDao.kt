package kaczmarek.moneycalculator.database.room.dao

import androidx.room.*
import kaczmarek.moneycalculator.database.room.model.BanknoteDBModel

@Dao
interface BanknoteDao : BaseDao<BanknoteDBModel> {

    @Query("SELECT * FROM banknotes")
    suspend fun getAll(): List<BanknoteDBModel>

    @Query("SELECT COUNT(*) FROM banknotes")
    suspend fun count(): Int

}