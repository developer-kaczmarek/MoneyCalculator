package kaczmarek.moneycalculator.di.modules

import dagger.Module
import dagger.Provides
import kaczmarek.moneycalculator.data.session.ISessionDatabase
import kaczmarek.moneycalculator.data.session.SessionRepository
import kaczmarek.moneycalculator.database.room.SessionDatabase
import kaczmarek.moneycalculator.database.room.db.RoomDatabase
import kaczmarek.moneycalculator.database.room.mapper.BanknoteDBModelMapper
import kaczmarek.moneycalculator.database.room.mapper.SessionDBModelMapper
import javax.inject.Singleton

@Module
object SessionModule {
    @Provides
    @Singleton
    fun provideSessionDBModelMapper(banknoteDBModelMapper: BanknoteDBModelMapper): SessionDBModelMapper {
        return SessionDBModelMapper(banknoteDBModelMapper)
    }

    @Provides
    @Singleton
    fun provideSessionDatabase(
        roomDatabase: RoomDatabase,
        mapper: SessionDBModelMapper
    ): ISessionDatabase {
        return SessionDatabase(roomDatabase, mapper)
    }

    @Provides
    @Singleton
    fun provideSessionRepository(database: SessionDatabase): SessionRepository {
        return SessionRepository(database = database)
    }
}