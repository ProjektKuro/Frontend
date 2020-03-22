package com.wimatt.ux.drinkanddare.utils

import java.util.*


class Period(
        var baseDate: Date,
        var years: Int,
        var months: Int,
        var days: Int
) {
    init {
        months += days / 365
        days %= 365
        years += months / 12
        months %= 12

    }

    fun matchesDate(date: Date): Boolean {
        if (baseDate < date) {
            return false
        }
        val matchDate = Date(date.time - baseDate.time)
        val calendar = GregorianCalendar()
        calendar.time = matchDate
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_YEAR)

        return (year % years + month % months + day % days) == 0
    }

    fun getFutureDays(minDate:Date, maxDate: Date): MutableList<Date> {
        var dateList = mutableListOf<Date>()
        val calendar = GregorianCalendar()
        calendar.time = baseDate
        while (true) {
            calendar.add(Calendar.YEAR, years)
            calendar.add(Calendar.MONTH, months)
            calendar.add(Calendar.DAY_OF_YEAR, days)
            if (calendar.time.time < maxDate.time) {
                dateList.add(calendar.time)
            } else {
                break
            }
        }
        return dateList
    }
}
