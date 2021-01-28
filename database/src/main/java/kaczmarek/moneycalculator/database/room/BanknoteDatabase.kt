package kaczmarek.moneycalculator.database.room

import kaczmarek.moneycalculator.data.banknote.model.Banknote
import kaczmarek.moneycalculator.data.banknote.port.IBanknoteDatabase
import kaczmarek.moneycalculator.database.room.db.RoomDatabase
import kaczmarek.moneycalculator.database.room.mapper.BanknoteDBModelMapper
import javax.inject.Inject

class BanknoteDatabase @Inject constructor(
    private val roomDatabase: RoomDatabase,
    private val mapper: BanknoteDBModelMapper
) : IBanknoteDatabase {

    override suspend fun getBanknotes(): List<Banknote> {
        return roomDatabase.banknoteDao().getAll().map { mapper.mapFromDBModel(it) }
    }

    override suspend fun updateVisibilityBanknote(idBanknote: Int, isVisible: Boolean) {
        roomDatabase.banknoteDao().updateVisibilityBanknote(idBanknote, isVisible)
    }

    override suspend fun insert(vararg obj: Banknote) {
        roomDatabase.banknoteDao().insert(*obj.map { mapper.mapToDBModel(it) }.toTypedArray())
    }

    override suspend fun update(vararg obj: Banknote) {
        roomDatabase.banknoteDao().update(*obj.map { mapper.mapToDBModel(it) }.toTypedArray())
    }

    override suspend fun delete(vararg obj: Banknote) {
        roomDatabase.banknoteDao().delete(*obj.map { mapper.mapToDBModel(it) }.toTypedArray())
    }

}