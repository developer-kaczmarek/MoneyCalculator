package kaczmarek.moneycalculator.core.data.storage.banknotes

import androidx.room.*

@Dao
interface BanknotesDao {

    @Query("SELECT * FROM banknotes")
    suspend fun getBanknotes(): List<BanknoteDbModel>

    @Query("UPDATE banknotes SET isShow = :isShow WHERE id = :id")
    suspend fun updateBanknoteVisibility(isShow: Boolean, id: Int)
}