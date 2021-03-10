package kaczmarek.moneycalculator.database.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "banknotes")
class BanknoteDBModel(
    val name: Float,
    val count: Int,
    val amount: Float,
    val backgroundColor: String,
    val isShow: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}