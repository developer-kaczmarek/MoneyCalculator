package kaczmarek.moneycalculator.domain.banknote.usecase

import kaczmarek.moneycalculator.domain.banknote.entity.BanknoteEntity
import kaczmarek.moneycalculator.domain.banknote.port.IBanknoteRepository

class GetTemporaryBanknotesListUseCase(private val repository: IBanknoteRepository) {
    fun invoke(): List<BanknoteEntity> {
        return repository.getBanknotesFromTemporaryStorage()
    }
}