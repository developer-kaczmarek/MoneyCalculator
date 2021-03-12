package kaczmarek.moneycalculator.database.room.mapper

import kaczmarek.moneycalculator.database.room.model.SessionDBModel
import kaczmarek.moneycalculator.domain.base.mapper.Mapper
import kaczmarek.moneycalculator.domain.session.entity.SessionEntity

class SessionDBModelMapper(private val mapper: BanknoteDBModelMapper) :
    Mapper<SessionDBModel, SessionEntity> {

    override fun mapToEntity(obj: SessionDBModel): SessionEntity {
        return SessionEntity(
            obj.date,
            obj.time,
            obj.totalAmount,
            obj.banknotes.map { mapper.mapToEntity(it) }.toList(),
            obj.id
        )
    }

    override fun mapFromEntity(obj: SessionEntity): SessionDBModel {
        return SessionDBModel(
            obj.date,
            obj.time,
            obj.totalAmount,
            obj.banknotes.map { mapper.mapFromEntity(it) }.toList()
        ).apply {
            obj.dbId?.let { id = it }
        }
    }
}