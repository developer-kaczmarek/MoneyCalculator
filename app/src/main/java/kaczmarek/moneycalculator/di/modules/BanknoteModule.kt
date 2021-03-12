package kaczmarek.moneycalculator.di.modules

import dagger.Module
import dagger.Provides
import kaczmarek.moneycalculator.data.banknote.BanknoteRepository
import kaczmarek.moneycalculator.data.banknote.IBanknoteDatabase
import kaczmarek.moneycalculator.database.room.BanknoteDatabase
import kaczmarek.moneycalculator.database.room.db.RoomDatabase
import kaczmarek.moneycalculator.database.room.mapper.BanknoteDBModelMapper
import kaczmarek.moneycalculator.di.services.TemporaryStorageService
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
    fun provideBanknoteRepository(
        database: BanknoteDatabase,
        temporaryStorageService: TemporaryStorageService
    ): BanknoteRepository {
        return BanknoteRepository(
            database = database,
            temporaryStorageService = temporaryStorageService
        )
    }
}