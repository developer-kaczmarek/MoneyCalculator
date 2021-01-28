package kaczmarek.moneycalculator.ui.base

interface Mapper<T, D> {

    fun mapToViewModel(obj: T): D

    fun mapFromViewModel(obj: D): T

}