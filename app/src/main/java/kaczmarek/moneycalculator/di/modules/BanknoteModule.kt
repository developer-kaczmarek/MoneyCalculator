package kaczmarek.moneycalculator.di.modules

import dagger.Module
import dagger.Provides
import kaczmarek.moneycalculator.data.banknote.mapper.BanknoteEntityMapper
import kaczmarek.moneycalculator.data.banknote.port.BanknoteRepository
import kaczmarek.moneycalculator.data.banknote.port.IBanknoteDatabase
import kaczmarek.moneycalculator.database.room.BanknoteDatabase
import kaczmarek.moneycalculator.database.room.db.RoomDatabase
import kaczmarek.moneycalculator.database.room.mapper.BanknoteDBModelMapper
import javax.inject.Singleton

@Module
object BanknoteModule {

    @Provides
    @Singleton
    fun provideBanknoteDBModelMapper(): BanknoteDBModelMapper {
        return BanknoteDBModelMapper()
    }

    @Provides
    @Singleton
    fun provideBanknoteDatabase(
        roomDatabase: RoomDatabase,
        mapper: BanknoteDBModelMapper
    ): IBanknoteDatabase {
        return BanknoteDatabase(roomDatabase, mapper)
    }

    @Provides
    @Singleton
    fun provideBanknoteEntityMapper(): BanknoteEntityMapper {
        return BanknoteEntityMapper()
    }

    @Provides
    @Singleton
    fun provideBanknoteRepository(
        database: BanknoteDatabase,
        entityMapper: BanknoteEntityMapper
    ): BanknoteRepository {
        return BanknoteRepository(database = database, mapper = entityMapper)
    }
}