package kaczmarek.moneycalculator.domain.banknote.usecase

import kaczmarek.moneycalculator.domain.banknote.entity.BanknoteEntity
import kaczmarek.moneycalculator.domain.banknote.port.IBanknoteRepository

class SaveBanknotesTemporaryUseCase(private val repository: IBanknoteRepository) {
    fun invoke(banknotes: List<BanknoteEntity>) {
        repository.saveBanknotesTemporary(banknotes)
    }
}