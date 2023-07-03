package com.xweather.sdkdemo.kotlin.view.weather

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aerisweather.aeris.model.AerisResponse
import com.aerisweather.aeris.model.ForecastPeriod
import com.aerisweather.aeris.response.ForecastsResponse
import com.aerisweather.aeris.util.FileUtil
import com.xweather.sdkdemo.kotlin.R
import com.xweather.sdkdemo.kotlin.data.weather.model.DayNightPeriod
import com.xweather.sdkdemo.kotlin.util.FormatUtil
import java.text.SimpleDateFormat
import java.util.*

class WeekendForecastScreen(val context: Context, val isMetrics: Boolean?) : IScreen {
    @Composable
    override fun Render(list: Any?) {
        (list as MutableList<*>?)?.get(0)?.apply {
            if ((this as AerisResponse).isSuccessfulWithResponses) {
                val fResponse = ForecastsResponse(
                    firstResponse,
                )
                val size = fResponse.periods.size / 2
                val periods = mutableListOf<DayNightPeriod>()
                for (i in 0 until size) {
                    val j = i * 2
                    periods.add(
                        DayNightPeriod(
                            fResponse.getPeriod(j),
                            fResponse.getPeriod(j + 1),
                        ),
                    )
                }
                LazyColumn(
                    contentPadding = PaddingValues(
                        horizontal = 0.dp,
                        vertical = 8.dp,
                    ),
                ) {
                    items(
                        items = periods,
                        itemContent = {
                            ComposeListCard(period = it)
                        },
                    )
                }
            }
        }
    }

    @Composable
    private fun ComposeListCard(period: DayNightPeriod) {
        Card(
            Modifier
                .fillMaxWidth()
                .background(Color.Black),
            shape = RoundedCornerShape(corner = CornerSize(6.dp)),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black),
            ) {
                Row(
                    modifier = Modifier
                        .padding(5.dp, 5.dp, 5.dp, 5.dp)
                        .fillMaxWidth()
                        .background(Color.Black),
                ) {
                    val dayDate = parseDate(period.day)
                    Text(
                        color = Color.White,
                        text = dayDate.first,
                        modifier = Modifier
                            .widthIn(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                    )
                    Text(
                        color = Color.White,
                        text = dayDate.second,
                        modifier = Modifier
                            .widthIn()
                            .padding(5.dp, 0.dp, 0.dp, 0.dp),
                        fontSize = 20.sp,
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(2.dp, 2.dp, 2.dp, 2.dp)
                        .fillMaxWidth()
                        .background(Color.Black),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(5.dp, 5.dp, 5.dp, 0.dp)
                            .weight(1f),
                    ) {
                        ComposeDayOrNight(period.day, isMetrics)
                    }
                    Column(
                        modifier = Modifier
                            .padding(5.dp, 5.dp, 5.dp, 0.dp)
                            .weight(1f),
                    ) {
                        ComposeDayOrNight(period.night, isMetrics)
                    }
                }
                Divider(color = Color(0xFF808080), thickness = 1.dp)
            }
        }
    }

    @Composable
    private fun ComposeDayOrNight(period: ForecastPeriod, isMetrics: Boolean?) {
        val color = if (period.isDay) {
            colorResource(R.color.light_gray)
        } else {
            colorResource(id = R.color.dark_blue)
        }
        Row(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 0.dp)
                .fillMaxWidth()
                .background(color),
            Arrangement.End,
        ) {
            Column() {
                val temp = if (period.isDay) {
                    Pair(period.maxTempC, period.maxTempF)
                } else {
                    Pair(period.minTempC, period.minTempF)
                }

                val tempStr =
                    FormatUtil.printDegree(context, isMetrics, temp)

                Text(
                    color = Color.White,
                    text = tempStr,
                    textAlign = TextAlign.Right,

                    modifier = Modifier
                        .padding(0.dp, 15.dp, 5.dp, 0.dp)
                        .widthIn(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                )
                Text(
                    color = Color.White,
                    text = period.weatherPrimary,
                    textAlign = TextAlign.Right,
                    modifier = Modifier
                        .padding(0.dp, 15.dp, 5.dp, 0.dp)
                        .widthIn(),
                    fontSize = 12.sp,
                )
            }
            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp)
                    .fillMaxWidth(),
                Alignment.CenterEnd,
            ) {
                Image(
                    painter = painterResource(
                        FileUtil.getDrawableByName(period.icon, context),
                    ),
                    contentDescription = "",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier.height(60.dp),
                )
            }
        }
        Row(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 0.dp)
                .fillMaxWidth()
                .background(color),
        ) {
            Text(
                color = Color.White,
                text = stringResource(R.string.winds),
                textAlign = TextAlign.Right,
                modifier = Modifier
                    .widthIn(),
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
            )
            Box(
                modifier = Modifier.fillMaxWidth(),
                Alignment.CenterEnd,
            ) {
                val tWind = period.windDir?.let {
                    when {
                        (period.windSpeedMaxMPH.toInt() < 5) -> stringResource(R.string.calm)
                        (period.windSpeedMinMPH.toInt() == period.windSpeedMaxMPH.toInt()) -> {
                            FormatUtil.printWindSpeed(
                                context,
                                isMetrics,
                                period.windDir,
                                Pair(period.windSpeedMaxKPH, period.windSpeedMaxMPH),
                            )
                        }
                        else -> {
                            FormatUtil.printWindSpeedMinMax(
                                context,
                                isMetrics,
                                period.windDir,
                                Pair(period.windSpeedMinKPH, period.windSpeedMinMPH),
                                Pair(period.windSpeedMaxKPH, period.windSpeedMaxMPH),
                            )
                        }
                    }
                } ?: stringResource(R.string.na)
                Text(
                    color = Color.White,
                    text = tWind,
                    textAlign = TextAlign.Right,

                    modifier = Modifier
                        .widthIn(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                )
            }
        }
        Row(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 0.dp)
                .fillMaxWidth()
                .background(color),
        ) {
            val precipLabel: String?
            val precipTextView: String?

            when {
                period.snowIN == null || period.snowIN.toDouble() == 0.0 -> {
                    precipLabel = stringResource(R.string.precip)
                    precipTextView = period.precipIN?.let {
                        FormatUtil.printPrecip(
                            context,
                            isMetrics,
                            Pair(period.precipMM, period.precipIN),
                        )
                    } ?: stringResource(
                        if (isMetrics == true) {
                            R.string.zero_cm
                        } else {
                            R.string.zero_in
                        },
                    )
                }
                else -> {
                    precipLabel = stringResource(R.string.snow)
                    precipTextView =
                        FormatUtil.printSnow(context, isMetrics, Pair(period.snowCM, period.snowIN))
                }
            }

            Text(
                color = Color.White,
                text = precipLabel,
                textAlign = TextAlign.Right,
                modifier = Modifier
                    .widthIn(),
                fontSize = 15.sp,
            )
            Box(
                modifier = Modifier.fillMaxWidth(),
                Alignment.CenterEnd,
            ) {
                Text(
                    color = Color.White,
                    text = precipTextView,
                    textAlign = TextAlign.Right,
                    modifier = Modifier
                        .widthIn(),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }

    private fun parseDate(period: ForecastPeriod): Pair<String, String> {
        val formatter = SimpleDateFormat(
            "yyyy-MM-dd",
            Locale.ENGLISH,
        )
        val iso: String = period.dateTimeISO
        val dateObj = formatter.parse(iso.split("T").toTypedArray()[0])
        val dayFormat = SimpleDateFormat(
            "EEE",
            Locale.ENGLISH,
        )
        val day = dayFormat.format(dateObj ?: "")
        val monthFormat = SimpleDateFormat(
            "MMM dd",
            Locale.ENGLISH,
        )
        val date = monthFormat.format(dateObj ?: "")
        return Pair<String, String>(day, date)
    }
}
