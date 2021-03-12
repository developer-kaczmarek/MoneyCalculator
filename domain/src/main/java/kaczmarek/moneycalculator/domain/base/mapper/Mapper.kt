package kaczmarek.moneycalculator.domain.base.mapper

interface Mapper<T, D> {
    fun mapToEntity(obj: T): D

    fun mapFromEntity(obj: D): T
}