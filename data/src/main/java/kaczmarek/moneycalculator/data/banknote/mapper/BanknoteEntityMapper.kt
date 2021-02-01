package kaczmarek.moneycalculator.data.banknote.mapper

import kaczmarek.moneycalculator.data.banknote.model.Banknote
import kaczmarek.moneycalculator.data.base.mapper.Mapper
import kaczmarek.moneycalculator.domain.banknote.entity.BanknoteEntity

class BanknoteEntityMapper : Mapper<Banknote, BanknoteEntity> {

    override fun mapToEntity(obj: Banknote): BanknoteEntity {
        return BanknoteEntity(obj.id, obj.name, obj.count, obj.amount, obj.backgroundColor, obj.isShow)
    }

    override fun mapFromEntity(obj: BanknoteEntity): Banknote {
        return Banknote(obj.id, obj.name, obj.count, obj.amount, obj.backgroundColor, obj.isShow)
    }

}