package com.example.piggerbank.Baza

import androidx.room.Embedded
import androidx.room.Relation

class CategoryWithMoney(
    @Embedded val category: Category,
    @Relation(
        parentColumn = "id",
        entityColumn = "id"
    )
    val money: List<Money>
)
