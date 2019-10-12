package com.kaczmarek.moneycalculator.di.services.database.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Created by Angelina Podbolotova on 06.10.2019.
 */
@Entity(tableName = "dates",
    foreignKeys = [ForeignKey(entity = Session::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("idSession"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Date(
    val date: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}