package com.xweather.sdkdemo.kotlin.util

import org.junit.Assert
import org.junit.Test
import java.time.ZonedDateTime

class DayDateUtilTest {

    @Test
    fun happy_path_isToday() {
        val str = ZonedDateTime.now().toString()
        val isToday = DayDateUtil.isToday(str)
        Assert.assertEquals(true, isToday)
    }

    @Test
    fun failure_tomorrow_isToday() {
        val str = ZonedDateTime.now().plusDays(1).toString()
        val isToday = DayDateUtil.isToday(str)
        Assert.assertEquals(false, isToday)
    }

    @Test
    fun failure_yesterday_isToday() {
        val str = ZonedDateTime.now().plusDays(-1).toString()
        val isToday = DayDateUtil.isToday(str)
        Assert.assertEquals(false, isToday)
    }

    @Test
    fun happy_path_isTomorrow() {
        val str = ZonedDateTime.now().plusDays(1).toString()
        val isToday = DayDateUtil.isTomorrow(str)
        Assert.assertEquals(true, isToday)
    }

    @Test
    fun failure_today_isTomorrow() {
        val str = ZonedDateTime.now().toString()
        val isToday = DayDateUtil.isTomorrow(str)
        Assert.assertEquals(false, isToday)
    }
}
