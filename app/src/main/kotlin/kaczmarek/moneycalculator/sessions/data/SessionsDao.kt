package kaczmarek.moneycalculator.sessions.data

import androidx.room.*

@Dao
interface SessionsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSession(session: SessionDbModel)

    @Query("SELECT * FROM sessions")
    suspend fun getSessions(): List<SessionDbModel>

    @Query("DELETE FROM sessions WHERE id = :id")
    suspend fun deleteSessionById(id: Int)

    @Query("DELETE FROM sessions WHERE date < :date")
    suspend fun deleteSessionsByDate(date: String)
}