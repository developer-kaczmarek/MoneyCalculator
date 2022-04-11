package kaczmarek.moneycalculator.core.banknote.data

import androidx.room.TypeConverter
import kaczmarek.moneycalculator.core.banknote.domain.DetailedBanknote
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class BanknoteConverter {
    @TypeConverter
    fun toDomain(dbModel: String): List<DetailedBanknote> {
        return Json.decodeFromString(dbModel)
    }

    @TypeConverter
    fun toDb(domain: List<DetailedBanknote>): String {
        return Json.encodeToString(domain)
    }
}