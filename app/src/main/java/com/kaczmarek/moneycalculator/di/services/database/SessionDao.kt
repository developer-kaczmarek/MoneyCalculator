package com.kaczmarek.moneycalculator.di.services.database

import androidx.room.*
import com.kaczmarek.moneycalculator.di.services.database.models.Session

/**
 * Created by Angelina Podbolotova on 04.10.2019.
 */
@Dao
interface SessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(session: Session)
}
