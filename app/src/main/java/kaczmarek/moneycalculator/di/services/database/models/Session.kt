package kaczmarek.moneycalculator.di.services.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Angelina Podbolotova on 06.10.2019.
 */
@Entity(tableName = "sessions")
data class Session(
    val date: String,
    val time: String,
    val totalAmount: Float,
    val banknotes: List<Banknote>
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}