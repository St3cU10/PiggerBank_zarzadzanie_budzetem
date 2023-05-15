package com.example.piggerbank.Baza

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Category(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo val id: Int?,
    @ColumnInfo val categoryName: String,
    @ColumnInfo val upperCategory: Int?
)