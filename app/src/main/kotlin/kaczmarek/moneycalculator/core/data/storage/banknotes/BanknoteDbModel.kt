package kaczmarek.moneycalculator.core.data.storage.banknotes

import androidx.room.Entity
import androidx.room.PrimaryKey
import kaczmarek.moneycalculator.core.domain.Banknote
import kaczmarek.moneycalculator.core.domain.BanknoteId
import kaczmarek.moneycalculator.core.domain.DetailedBanknote
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "banknotes")
data class BanknoteDbModel(
    val name: String,
    val backgroundColor: String,
    val value: Float,
    val isShow: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

fun BanknoteDbModel.toBanknote() = Banknote(
    id = BanknoteId(id.toString()),
    name = name,
    isShow = isShow,
    denomination = value.toDouble()
)

fun BanknoteDbModel.toDetailedBanknote() = DetailedBanknote(
    id = BanknoteId(id.toString()),
    name = name,
    isShow = isShow,
    denomination = value.toDouble(),
    backgroundColor = ("ff" + backgroundColor.removePrefix("#").lowercase()).toLong(16),
    count = "0",
    amount = "0"
)