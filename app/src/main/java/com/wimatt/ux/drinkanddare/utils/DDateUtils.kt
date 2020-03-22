package com.wimatt.ux.drinkanddare.utils

import java.text.SimpleDateFormat
import java.util.*

class DDateUtils {
    companion object {
        fun dateToString(date: Date): String {
            val fmtOut = SimpleDateFormat.getDateInstance()
            return fmtOut.format(date)
        }

        fun dateToUserString(date: Date): String {
            val fmtOut = SimpleDateFormat.getDateInstance(1 , Locale.getDefault())
            return fmtOut.format(date)
        }

        fun removeTime(date: Date): Date {
            val calendar = GregorianCalendar()
            calendar.time = date
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            return calendar.time
        }

        fun getDateRange(date: Date, subDays: Int): Date {
            val calendar = GregorianCalendar()
            calendar.time = date
            calendar.set(Calendar.HOUR_OF_DAY, 0)
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)
            calendar.add(Calendar.DAY_OF_YEAR, subDays)
            return calendar.time
        }

        fun sameDate(dateY: Date, dateX: Date): Boolean {
            val calendarX = GregorianCalendar()
            calendarX.time = dateX
            val calendarY = GregorianCalendar()
            calendarY.time = dateY
            return calendarX.get(Calendar.DAY_OF_YEAR) == calendarY.get(Calendar.DAY_OF_YEAR)
                    && calendarX.get(Calendar.MONTH) == calendarY.get(Calendar.MONTH)
                    && calendarX.get(Calendar.YEAR) == calendarY.get(Calendar.YEAR)
        }

        fun stringToDate(dateStr: String): Date {
            val fmt = SimpleDateFormat.getDateInstance()
            val date = fmt.parse(dateStr)
            return date
        }
    }
}