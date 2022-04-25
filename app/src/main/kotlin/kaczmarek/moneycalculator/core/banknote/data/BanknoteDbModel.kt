package kaczmarek.moneycalculator.core.banknote.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kaczmarek.moneycalculator.core.banknote.domain.Banknote
import kaczmarek.moneycalculator.core.banknote.domain.BanknoteId
import kaczmarek.moneycalculator.core.banknote.domain.DetailedBanknote
import kaczmarek.moneycalculator.core.utils.toFormattedAmount
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "banknotes")
data class BanknoteDbModel(
    val name: Double,
    val count: Int,
    val amount: Double,
    val backgroundColor: String,
    val textColor: String,
    val isShow: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

fun BanknoteDbModel.toBanknote(): Banknote {
    val denomination = name
    val formattedName =
        (if (denomination < 1) denomination * 100 else denomination).toFormattedAmount()

    return Banknote(
        id = BanknoteId(id.toString()),
        name = formattedName,
        isShow = isShow,
        denomination = denomination
    )
}

fun BanknoteDbModel.toDetailedBanknote(): DetailedBanknote {
    val denomination = name
    val formattedName =
        (if (denomination < 1) denomination * 100 else denomination).toFormattedAmount()

    return DetailedBanknote(
        id = BanknoteId(id.toString()),
        name = formattedName,
        isShow = isShow,
        denomination = denomination,
        backgroundColor = ("ff" + backgroundColor.removePrefix("#").lowercase()).toLong(16),
        count = "0",
        amount = "0"
    )
}