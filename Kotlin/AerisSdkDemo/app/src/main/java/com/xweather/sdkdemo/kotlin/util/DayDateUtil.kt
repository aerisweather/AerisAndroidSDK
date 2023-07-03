package com.xweather.sdkdemo.kotlin.util

import java.time.Instant
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.util.*

object DayDateUtil {
    fun isToday(iso: String): Boolean {
        val date = ZonedDateTime.parse(iso)
        return date.dayOfMonth.equals(ZonedDateTime.now().dayOfMonth)
    }

    fun isTomorrow(iso: String): Boolean {
        val date = ZonedDateTime.parse(iso)
        return date.dayOfMonth.equals(ZonedDateTime.now().plusDays(1).dayOfMonth)
    }

    fun convert(iso: String): Date {
        val timeFormatter: DateTimeFormatter = DateTimeFormatter.ISO_DATE_TIME
        val accessor: TemporalAccessor = timeFormatter.parse(iso)
        val date = Date.from(Instant.from(accessor))
        return date
    }

    fun convertZone(iso: String) = ZonedDateTime.parse(iso)

    fun printTime(zonedDateTime: ZonedDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("hh:mm:ss a")
        return zonedDateTime.format(formatter)
    }

    fun printDateTime(zonedDateTime: ZonedDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy - HH:mm:ss a")
        return zonedDateTime.format(formatter)
    }
}
