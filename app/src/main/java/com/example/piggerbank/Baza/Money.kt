package com.example.piggerbank.Baza

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Calendar
import java.util.Date

@Entity
data class Money(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo val id: Int?,
    @ColumnInfo val moneyValue: Double,
    @ColumnInfo val moneyDescription: String?,
    @ColumnInfo val moneyCategory_id: Int?,
    @ColumnInfo val moneyDate : Date
)