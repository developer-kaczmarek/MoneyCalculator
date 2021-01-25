package kaczmarek.moneycalculator.database.room.mapper

import kaczmarek.moneycalculator.data.banknote.model.Banknote
import kaczmarek.moneycalculator.database.room.model.BanknoteDBModel

class BanknoteDBModelMapper : Mapper<Banknote, BanknoteDBModel> {
    override fun mapToDBModel(obj: Banknote): BanknoteDBModel {
        return BanknoteDBModel(
            obj.name,
            obj.count,
            obj.amount,
            obj.backgroundColor,
            obj.textColor,
            obj.isShow
        )
    }

    override fun mapFromDBModel(obj: BanknoteDBModel): Banknote {
        return Banknote(
            obj.id,
            obj.name,
            obj.count,
            obj.amount,
            obj.backgroundColor,
            obj.textColor,
            obj.isShow
        )
    }
}