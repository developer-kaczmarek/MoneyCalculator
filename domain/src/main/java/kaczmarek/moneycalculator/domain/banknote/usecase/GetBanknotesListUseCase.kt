package kaczmarek.moneycalculator.domain.banknote.usecase

import kaczmarek.moneycalculator.domain.banknote.entity.BanknoteEntity
import kaczmarek.moneycalculator.domain.banknote.port.IBanknoteRepository

class GetBanknotesListUseCase(private val repository: IBanknoteRepository) {
    suspend fun invoke(): List<BanknoteEntity> {
        return repository.getBanknotes()
    }
}