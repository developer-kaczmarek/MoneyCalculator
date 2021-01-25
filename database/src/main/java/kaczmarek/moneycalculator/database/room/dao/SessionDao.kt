package kaczmarek.moneycalculator.database.room.dao

import androidx.room.*
import kaczmarek.moneycalculator.database.room.model.SessionDBModel

@Dao
interface SessionDao : BaseDao<SessionDBModel> {

    @Query("SELECT * FROM sessions")
    suspend fun getAll(): List<SessionDBModel>

    @Query("DELETE FROM sessions WHERE date <=:deleteDate")
    suspend fun deleteSessionByDate(deleteDate: String)

}
