package kaczmarek.moneycalculator.core.data.storage.banknotes

import androidx.room.Entity
import androidx.room.PrimaryKey
import kaczmarek.moneycalculator.core.domain.Banknote
import kaczmarek.moneycalculator.core.domain.BanknoteId
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

fun BanknoteDbModel.toDomain() = Banknote(
    id = BanknoteId(id.toString()),
    name = name,
    isShow = isShow,
    backgroundColor = backgroundColor,
    value = value
)

fun Banknote.toDb() = BanknoteDbModel(
    name = name,
    isShow = isShow,
    backgroundColor = backgroundColor,
    value = value
)