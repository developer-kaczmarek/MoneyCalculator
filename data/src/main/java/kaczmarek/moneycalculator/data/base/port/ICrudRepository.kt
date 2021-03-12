package kaczmarek.moneycalculator.data.base.port

interface ICrudRepository<T> {
    suspend fun insert(vararg obj: T)
    suspend fun update(vararg obj: T)
    suspend fun delete(vararg obj: T)
}