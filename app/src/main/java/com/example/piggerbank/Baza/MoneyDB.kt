package com.example.piggerbank.Baza

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters

@Database(
    entities = [
        Money::class,
        Category::class
            ],
    version = 1
)

@TypeConverters(Converters::class)

abstract class MoneyDB: RoomDatabase(){
    abstract fun moneyDao() : MoneyDao

    companion object{
        @Volatile
        private var INSTANCE: MoneyDB? = null

        fun getInstance(context: Context): MoneyDB{
            synchronized(this){
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    MoneyDB::class.java,
                    "money_db"
                ).allowMainThreadQueries().build().also {
                    INSTANCE = it
                }
            }
        }
    }
}