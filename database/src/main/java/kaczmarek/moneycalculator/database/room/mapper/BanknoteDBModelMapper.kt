package kaczmarek.moneycalculator.database.room.mapper

import kaczmarek.moneycalculator.database.room.model.BanknoteDBModel
import kaczmarek.moneycalculator.domain.banknote.entity.BanknoteEntity
import kaczmarek.moneycalculator.domain.base.mapper.Mapper

class BanknoteDBModelMapper : Mapper<BanknoteDBModel, BanknoteEntity> {
    override fun mapToEntity(obj: BanknoteDBModel): BanknoteEntity {
        return BanknoteEntity(
            obj.id,
            obj.name,
            obj.count,
            obj.amount,
            obj.backgroundColor,
            obj.isShow
        )
    }

    override fun mapFromEntity(obj: BanknoteEntity): BanknoteDBModel {
        return BanknoteDBModel(obj.name, obj.count, obj.amount, obj.backgroundColor, obj.isShow)
    }
}