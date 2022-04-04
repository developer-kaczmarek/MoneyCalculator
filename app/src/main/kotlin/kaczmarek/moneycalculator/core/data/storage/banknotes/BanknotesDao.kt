package kaczmarek.moneycalculator.core.data.storage.banknotes

import androidx.room.*

@Dao
interface BanknotesDao {

    @Query("SELECT * FROM banknotes")
    suspend fun getBanknotes(): List<BanknoteDbModel>

    @Query("SELECT * FROM banknotes WHERE isShow = 1")
    suspend fun getBanknotesByVisibility(): List<BanknoteDbModel>

    @Query("SELECT * FROM banknotes WHERE id=:id")
    suspend fun getBanknoteById(id: Int): BanknoteDbModel

    @Query("UPDATE banknotes SET isShow = :isShow WHERE id = :id")
    suspend fun updateBanknoteVisibility(isShow: Boolean, id: Int)
}