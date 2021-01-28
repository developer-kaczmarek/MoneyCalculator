package kaczmarek.moneycalculator.database.room.mapper

import kaczmarek.moneycalculator.data.session.model.Session
import kaczmarek.moneycalculator.database.room.model.SessionDBModel

class SessionDBModelMapper(private val mapper: BanknoteDBModelMapper) :
    Mapper<Session, SessionDBModel> {
    override fun mapToDBModel(obj: Session): SessionDBModel {
        return SessionDBModel(
            obj.date,
            obj.time,
            obj.totalAmount,
            obj.banknotes.map { mapper.mapToDBModel(it) }.toList()
        )
    }

    override fun mapFromDBModel(obj: SessionDBModel): Session {
        return Session(
            obj.date,
            obj.time,
            obj.totalAmount,
            obj.banknotes.map { mapper.mapFromDBModel(it) }.toList()
        )
    }
}