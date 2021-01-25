package kaczmarek.moneycalculator.database.room.mapper

interface Mapper<T, D> {

    fun mapToDBModel(obj: T): D

    fun mapFromDBModel(obj: D): T

}