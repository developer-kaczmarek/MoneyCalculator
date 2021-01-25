package kaczmarek.moneycalculator.database.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kaczmarek.moneycalculator.database.room.model.BanknoteDBModel

class BanknoteConverter {
    @TypeConverter
    fun fromString(value: String): List<BanknoteDBModel> =
        Gson().fromJson(value, object : TypeToken<List<BanknoteDBModel>>() {}.type)

    @TypeConverter
    fun fromArrayList(list: List<BanknoteDBModel>): String = Gson().toJson(list)
}
