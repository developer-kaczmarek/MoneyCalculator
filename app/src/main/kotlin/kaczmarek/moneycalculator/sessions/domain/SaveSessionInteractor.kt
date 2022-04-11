package kaczmarek.moneycalculator.sessions.domain

import kaczmarek.moneycalculator.core.banknote.domain.DetailedBanknote
import kaczmarek.moneycalculator.core.utils.getFormattedCurrentDate
import kaczmarek.moneycalculator.core.utils.getFormattedCurrentTime
import kaczmarek.moneycalculator.sessions.data.SessionsStorage
import java.util.*

class SaveSessionInteractor(private val sessionsStorage: SessionsStorage) {

    suspend fun execute(totalAmount: Double, banknote: List<DetailedBanknote>) {
        val date = Date()
        return sessionsStorage.saveSession(
            date = getFormattedCurrentDate(),
            time = getFormattedCurrentTime(),
            totalAmount = totalAmount,
            banknotes = banknote
        )
    }
}