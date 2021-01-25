package kaczmarek.moneycalculator.database.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sessions")
data class SessionDBModel(
    val date: String,
    val time: String,
    val totalAmount: Double,
    val banknotes: List<BanknoteDBModel>
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}