package kaczmarek.moneycalculator.database.room

import kaczmarek.moneycalculator.data.banknote.IBanknoteDatabase
import kaczmarek.moneycalculator.database.room.db.RoomDatabase
import kaczmarek.moneycalculator.database.room.mapper.BanknoteDBModelMapper
import kaczmarek.moneycalculator.domain.banknote.entity.BanknoteEntity
import javax.inject.Inject

class BanknoteDatabase @Inject constructor(
    private val roomDatabase: RoomDatabase,
    private val mapper: BanknoteDBModelMapper
) : IBanknoteDatabase {
    override suspend fun getBanknotes(): List<BanknoteEntity> {
        return roomDatabase.banknoteDao().getAll().map { mapper.mapToEntity(it) }
    }

    override suspend fun updateVisibilityBanknote(idBanknote: Int, isVisible: Boolean) {
        roomDatabase.banknoteDao().updateVisibilityBanknote(idBanknote, isVisible)
    }

    override suspend fun insert(vararg obj: BanknoteEntity) {
        roomDatabase.banknoteDao().insert(*obj.map { mapper.mapFromEntity(it) }.toTypedArray())
    }

    override suspend fun update(vararg obj: BanknoteEntity) {
        roomDatabase.banknoteDao().update(*obj.map { mapper.mapFromEntity(it) }.toTypedArray())
    }

    override suspend fun delete(vararg obj: BanknoteEntity) {
        roomDatabase.banknoteDao().delete(*obj.map { mapper.mapFromEntity(it) }.toTypedArray())
    }
}