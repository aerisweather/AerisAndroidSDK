package com.example.demoaerisproject.util

import java.time.ZonedDateTime

object DayDateUtil {
    fun isToday(iso: String): Boolean {
        val date = ZonedDateTime.parse(iso)
        return date.dayOfMonth.equals(ZonedDateTime.now().dayOfMonth)
    }

    fun isTomorrow(iso: String): Boolean {
        val date = ZonedDateTime.parse(iso)
        return date.dayOfMonth.equals(ZonedDateTime.now().plusDays(1).dayOfMonth)
    }
}