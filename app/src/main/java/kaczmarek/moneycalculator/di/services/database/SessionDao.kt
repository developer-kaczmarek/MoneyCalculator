package kaczmarek.moneycalculator.di.services.database

import androidx.room.*
import kaczmarek.moneycalculator.di.services.database.models.Session

/**
 * Created by Angelina Podbolotova on 04.10.2019.
 */
@Dao
interface SessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(session: Session)

    @Query("SELECT * FROM sessions")
    fun getAll(): List<Session>

    @Delete
    fun deleteSession(session: Session)
}
