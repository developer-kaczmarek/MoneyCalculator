package kaczmarek.moneycalculator.domain.banknote.usecase

import kaczmarek.moneycalculator.domain.banknote.port.IBanknoteRepository

class UpdateVisibilityBanknoteUseCase(private val repository: IBanknoteRepository) {
    suspend fun invoke(idBanknote: Int, isVisible: Boolean) {
        repository.updateVisibilityBanknote(idBanknote, isVisible)
    }
}