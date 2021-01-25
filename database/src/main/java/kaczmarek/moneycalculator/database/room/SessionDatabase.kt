package kaczmarek.moneycalculator.database.room

import kaczmarek.moneycalculator.data.session.model.Session
import kaczmarek.moneycalculator.data.session.port.ISessionDatabase
import kaczmarek.moneycalculator.database.room.db.RoomDatabase
import kaczmarek.moneycalculator.database.room.mapper.SessionDBModelMapper
import javax.inject.Inject

class SessionDatabase @Inject constructor(
    private val roomDatabase: RoomDatabase,
    private val mapper: SessionDBModelMapper
) : ISessionDatabase {

    override suspend fun getSessions(): List<Session> {
        return roomDatabase.sessionDao().getAll().map { mapper.mapFromDBModel(it) }
    }

    override suspend fun insert(vararg obj: Session) {
        roomDatabase.sessionDao().insert(*obj.map { mapper.mapToDBModel(it) }.toTypedArray())
    }

    override suspend fun update(vararg obj: Session) {
        roomDatabase.sessionDao().update(*obj.map { mapper.mapToDBModel(it) }.toTypedArray())
    }

    override suspend fun delete(vararg obj: Session) {
        roomDatabase.sessionDao().delete(*obj.map { mapper.mapToDBModel(it) }.toTypedArray())
    }

    override suspend fun deleteSessionByDate(date: String) {
        roomDatabase.sessionDao().deleteSessionByDate(date)
    }

}