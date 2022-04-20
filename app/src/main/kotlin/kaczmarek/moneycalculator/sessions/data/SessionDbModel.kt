package kaczmarek.moneycalculator.sessions.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kaczmarek.moneycalculator.core.banknote.domain.DetailedBanknote
import kaczmarek.moneycalculator.sessions.domain.Session
import kaczmarek.moneycalculator.sessions.domain.SessionId
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "sessions")
data class SessionDbModel(
    val date: String,
    val time: String,
    val totalAmount: Double,
    val banknotes: List<DetailedBanknote>
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

fun SessionDbModel.toSession(): Session {
    return Session(
        id = SessionId(id.toString()),
        date = date,
        time = time,
        totalAmount = totalAmount,
        banknotes = banknotes,
        totalCount = banknotes.sumOf { it.count.toInt() }
    )
}