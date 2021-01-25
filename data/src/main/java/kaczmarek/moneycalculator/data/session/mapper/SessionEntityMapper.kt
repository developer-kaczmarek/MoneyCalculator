package kaczmarek.moneycalculator.data.session.mapper

import kaczmarek.moneycalculator.data.banknote.mapper.BanknoteEntityMapper
import kaczmarek.moneycalculator.data.base.mapper.Mapper
import kaczmarek.moneycalculator.data.session.model.Session
import kaczmarek.moneycalculator.domain.session.entity.SessionEntity

class SessionEntityMapper(private val banknoteEntityMapper: BanknoteEntityMapper) :
    Mapper<Session, SessionEntity> {

    override fun mapToEntity(obj: Session): SessionEntity {
        return SessionEntity(
            obj.id,
            obj.date,
            obj.time,
            obj.totalAmount,
            obj.banknotes.map { banknoteEntityMapper.mapToEntity(it) }.toList()
        )
    }

    override fun mapFromEntity(obj: SessionEntity): Session {
        return Session(
            obj.id,
            obj.date,
            obj.time,
            obj.totalAmount,
            obj.banknotes.map { banknoteEntityMapper.mapFromEntity(it) }.toList()
        )
    }

}