package com.example.piggerbank.Baza

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MoneyDao {

    @Insert(entity = Money::class, onConflict = OnConflictStrategy.IGNORE)
    fun insertMoney(money: Money)

    @Insert(entity = Category::class, onConflict = OnConflictStrategy.IGNORE)
    fun insertCategory(category: Category)

    @Query("SELECT categoryName FROM Category")
    fun getCategories(): List<String>

    @Query("SELECT id FROM Category WHERE categoryName LIKE :name LIMIT 1")
    fun getId(name: String): Int?

    @Query("SELECT categoryName FROM Category WHERE categoryName Like :name LIMIT 1")
    fun getCat(name: String): String?

    @Query("SELECT  Money.moneyValue, Money.moneyDescription, Category.categoryName" +
            " FROM Money INNER JOIN Category ON Money.moneyCategory_id=Category.id")
    fun getMoneyAll(): List<Money>
}