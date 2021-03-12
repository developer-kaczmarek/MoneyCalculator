package kaczmarek.moneycalculator.database.room

import kaczmarek.moneycalculator.data.session.ISessionDatabase
import kaczmarek.moneycalculator.database.room.db.RoomDatabase
import kaczmarek.moneycalculator.database.room.mapper.SessionDBModelMapper
import kaczmarek.moneycalculator.domain.session.entity.SessionEntity
import javax.inject.Inject

class SessionDatabase @Inject constructor(
    private val roomDatabase: RoomDatabase,
    private val mapper: SessionDBModelMapper
) : ISessionDatabase {
    override suspend fun getSessions(): List<SessionEntity> {
        return roomDatabase.sessionDao().getAll().map { mapper.mapToEntity(it) }
    }

    override suspend fun insert(vararg obj: SessionEntity) {
        roomDatabase.sessionDao().insert(*obj.map { mapper.mapFromEntity(it) }.toTypedArray())
    }

    override suspend fun update(vararg obj: SessionEntity) {
        roomDatabase.sessionDao().update(*obj.map { mapper.mapFromEntity(it) }.toTypedArray())
    }

    override suspend fun delete(vararg obj: SessionEntity) {
        roomDatabase.sessionDao().delete(*obj.map { mapper.mapFromEntity(it) }.toTypedArray())
    }

    override suspend fun deleteSessionByDate(date: String) {
        roomDatabase.sessionDao().deleteSessionByDate(date)
    }
}