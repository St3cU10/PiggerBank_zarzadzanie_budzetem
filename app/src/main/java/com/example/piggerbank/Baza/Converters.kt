package com.example.piggerbank.Baza

import androidx.room.TypeConverter
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar

class Converters {

    @TypeConverter
    fun fromDateToLong(date: Date) : Long{
        return date.time
    }

    @TypeConverter
    fun fromLongToDate(timeLong: Long) : Date{

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeLong
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH) + 1 // dodajemy 1, ponieważ miesiące w klasie Calendar są numerowane od 0 do 11
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        calendar.set(year, month-1, day)
        return Date(calendar.timeInMillis)
    }
}