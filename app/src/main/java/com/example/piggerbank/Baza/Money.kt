package com.example.piggerbank.Baza

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Money(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo val id: Int?,
    @ColumnInfo val moneyValue: Double,
    @ColumnInfo val moneyDescription: String?,
    @ColumnInfo val moneyCategory_id: Int?
)