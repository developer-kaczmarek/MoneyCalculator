package kaczmarek.moneycalculator.data.base.mapper

interface Mapper<T, D> {

    fun mapToEntity(obj: T): D

    fun mapFromEntity(obj: D): T

}