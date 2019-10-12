package com.kaczmarek.moneycalculator.di.services.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Angelina Podbolotova on 04.10.2019.
 */
@Entity(tableName = "banknotes")
data class Banknote(
    val name: Float,
    var count: Int,
    var amount: Float,
    val backgroundColor: String,
    val textColor: String,
    var isShow: Boolean
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}