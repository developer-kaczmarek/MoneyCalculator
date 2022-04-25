package kaczmarek.moneycalculator.core.banknote.data

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class BanknoteConverter {
    @TypeConverter
    fun toDomain(dbModel: String): List<BanknoteDbModel> {
        return Json.decodeFromString(dbModel)
    }

    @TypeConverter
    fun toDb(domain: List<BanknoteDbModel>): String {
        return Json.encodeToString(domain)
    }
}