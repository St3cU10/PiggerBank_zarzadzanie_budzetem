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
    fun getId(name: String?): Int?

    @Query("SELECT categoryName FROM Category WHERE categoryName Like :name LIMIT 1")
    fun getCat(name: String): String?

    @Query("SELECT  Money.moneyValue, Money.moneyDescription, Category.categoryName, Money.moneyDate" +
            " FROM Money INNER JOIN Category ON Money.moneyCategory_id=Category.id")
    fun getMoneyAll(): List<Money>

    @Query("SELECT id FROM Money")
    fun getAllMoneyId() : Array<Int>

    @Query("SELECT moneyDescription FROM Money")
    fun getAllMoneyName() : Array<String>

    @Query("SELECT moneyValue FROM Money")
    fun getAllMoneyValue() : Array<Double>

    @Query("SELECT Category.categoryName FROM Money INNER JOIN Category ON Money.moneyCategory_id = Category.id")
    fun getAllMoneyCategory() : Array<String>

    @Query("SELECT moneyDate FROM Money")
    fun getAllMoneyDate() : Array<String>

    @Query("SELECT categoryName FROM Category")
    fun getAllCategoryName() : Array<String>

    @Query("SELECT b.categoryName FROM Category a LEFT JOIN Category b ON a.upperCategory = b.id")
    fun getAllUpperCategory() : Array<String>


    //  ZNAJDOWANIE KONKRETNEJ KASY PO ID
    @Query("SELECT moneyDescription FROM Money WHERE id LIKE :monId")
    fun getOneMoneyDescription(monId : Int) : String

    @Query("SELECT Category.categoryName FROM Money INNER JOIN Category ON " +
            "Money.moneyCategory_id = Category.id WHERE Money.id LIKE :monId")
    fun getOneMoneyCategory(monId: Int) : String

    @Query("SELECT moneyValue FROM Money WHERE id LIKE :monId")
    fun getOneMoneyValue(monId : Int) : Double

    @Query("SELECT moneyDate FROM Money WHERE id LIKE :monId")
    fun getOneMoneyDate(monId : Int) : String


    @Query("UPDATE Money SET moneyDescription=:newName, moneyValue=:newValue, moneyCategory_id=:newCat," +
            "moneyDate=:newDate WHERE id LIKE :monId")
    fun updateMoney(newName : String, newValue : Double, newCat : Int?,
    newDate : String, monId : Int)

   // @Query("SELECT SUM(moneyValue) AS suma FROM Money Where moneyCategory_id like :catnr")
    //fun calculateSum(catnr: Int = 1): Double
}