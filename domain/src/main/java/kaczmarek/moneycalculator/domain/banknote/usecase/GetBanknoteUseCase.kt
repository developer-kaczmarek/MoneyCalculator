package kaczmarek.moneycalculator.domain.banknote.usecase

import kaczmarek.moneycalculator.domain.banknote.entity.BanknoteEntity
import kaczmarek.moneycalculator.domain.banknote.port.IBanknoteRepository

class GetBanknoteUseCase(private val repository: IBanknoteRepository) {

    suspend fun getList(): List<BanknoteEntity> {
        return repository.getBanknotes()
    }

}