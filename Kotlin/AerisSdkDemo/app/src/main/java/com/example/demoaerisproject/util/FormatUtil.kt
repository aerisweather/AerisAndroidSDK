package com.example.demoaerisproject.util

import android.content.Context
import com.aerisweather.aeris.util.WeatherUtil
import com.example.demoaerisproject.R
import kotlin.math.roundToInt

object FormatUtil {

    fun printDegree(context: Context, isMetric: Boolean?, temp: Pair<Number?, Number?>): String {
        val n = (if (isMetric == true) {
            temp.first
        } else {
            temp.second
        })?.toDouble()?.roundToInt()
        val numString = WeatherUtil.appendDegree(n)
        return n?.let {
            if (isMetric == true) {
                context.resources.getString(
                    R.string.temp_celsius, numString
                )
            } else {
                context.resources.getString(
                    R.string.temp_fahrenheit, numString
                )
            }
        } ?: numString // NA
    }

    fun printWindSpeed(
        context: Context,
        isMetric: Boolean?,
        windDir: String,
        max: Pair<Number?, Number?>
    ): String {
        val n = if (isMetric == true) {
            max.first
        } else {
            max.second
        }
        return n?.let {
            if (isMetric == true) {
                context.resources.getString(
                    R.string.kph,
                    windDir, max.first?.toInt()
                )
            } else {
                context.resources.getString(
                    R.string.mph,
                    windDir, max.second?.toInt()
                )
            }
        } ?: context.resources.getString(R.string.na)
    }

    fun printWindSpeedMinMax(
        context: Context,
        isMetric: Boolean?,
        windDir: String,
        min: Pair<Number?, Number?>,
        max: Pair<Number?, Number?>
    ): String {
        return if (isMetric == true) {
            context.resources.getString(
                R.string.kph_plus,
                windDir, min.first?.toInt(),
                max.first?.toInt()
            )
        } else {
            context.resources.getString(
                R.string.mph_plus,
                windDir, min.first?.toInt(),
                max.first?.toInt()
            )
        }
    }

    fun printSnow(
        context: Context,
        isMetric: Boolean?,
        precip: Pair<Number?, Number?>
    ): String {
        return if (isMetric == true) {
            context.resources.getString(
                R.string.cm_plus,
                precip.first?.toString() ?: R.string.zero_value
            )
        } else {
            context.resources.getString(
                R.string.in_plus,
                precip.second?.toString() ?: R.string.zero_value
            )
        }
    }

    fun printPrecip(
        context: Context,
        isMetric: Boolean?,
        precip: Pair<Number?, Number?>
    ): String {
        return if (isMetric == true) {
            context.resources.getString(
                R.string.mm_plus,
                precip.first?.toString() ?: R.string.zero_value
            )
        } else {
            context.resources.getString(
                R.string.in_plus,
                precip.second?.toString() ?: R.string.zero_value
            )
        }
    }

    fun printPressure(
        context: Context,
        isMetric: Boolean?,
        pressure: Pair<Number?, Number?>
    ): String {
        val n = if (isMetric == true) {
            pressure.first
        } else {
            pressure.second
        }
        return n?.let {
            if (isMetric == true) {
                context.resources.getString(
                    R.string.milli_bar_plus,
                    n.toString() ?: R.string.null_value
                )
            } else {
                context.resources.getString(
                    R.string.in_plus,
                    n.toString() ?: R.string.null_value
                )
            }
        } ?: context.resources.getString(R.string.na)
    }
}