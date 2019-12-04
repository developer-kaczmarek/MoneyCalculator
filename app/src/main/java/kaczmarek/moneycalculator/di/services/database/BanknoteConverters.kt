package kaczmarek.moneycalculator.di.services.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kaczmarek.moneycalculator.di.services.database.models.Banknote

/**
 * Created by Angelina Podbolotova on 12.10.2019.
 */
class BanknoteConverters {
    @TypeConverter
    fun fromString(value: String): List<Banknote> =
        Gson().fromJson(value, object : TypeToken<List<Banknote>>() {}.type)

    @TypeConverter
    fun fromArrayLisr(list: List<Banknote>): String = Gson().toJson(list)
}