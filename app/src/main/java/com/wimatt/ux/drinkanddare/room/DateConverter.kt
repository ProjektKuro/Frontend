package com.wimatt.ux.drinkanddare.room

import androidx.room.TypeConverter
import java.util.*


class DateConverter {
    @TypeConverter
    fun toDate(dateLong: Long): Date {
        return Date(dateLong)
    }

    @TypeConverter
    fun fromDate(date: Date?): Long {
        date?.let {
            return it.time
        } ?: kotlin.run {
            return 0
        }
    }
}