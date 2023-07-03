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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aerisweather.aeris.model.AerisResponse
import com.aerisweather.aeris.model.ConditionPeriod
import com.aerisweather.aeris.model.ForecastPeriod
import com.aerisweather.aeris.response.ConditionsResponse
import com.aerisweather.aeris.response.ForecastsResponse
import com.aerisweather.aeris.response.PlacesResponse
import com.aerisweather.aeris.util.FileUtil
import com.aerisweather.aeris.util.WeatherUtil
import com.xweather.sdkdemo.kotlin.R
import com.xweather.sdkdemo.kotlin.data.weather.model.DayNightPeriod
import com.xweather.sdkdemo.kotlin.util.FormatUtil
import com.xweather.sdkdemo.kotlin.view.ComposeGrid

class DetailedScreen(val context: Context, val isMetrics: Boolean?) : IScreen {

    @Composable
    override fun Render(list: Any?) {
        (list as MutableList<*>?)?.apply {
            val condResponse = ConditionsResponse(
                (get(0) as AerisResponse).firstResponse,
            )
            val condPeriod = condResponse.getPeriod(0)

            val pResponse = PlacesResponse(
                (get(1) as AerisResponse).firstResponse,
            )

            val fResponse = ForecastsResponse(
                (get(2) as AerisResponse).firstResponse,
            )

            val listResponse = ForecastsResponse(
                (get(3) as AerisResponse).firstResponse,
            )

            Card(
                Modifier
                    .fillMaxWidth()
                    .heightIn()
                    .padding(vertical = 5.dp, horizontal = 5.dp)
                    .background(Color.Black),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn()
                        .background(Color(0xFF3f3f3f)),
                ) {
                    ComposePlace(pResponse)
                    ComposeCondition(condPeriod)
                    Divider(color = Color.Black, thickness = 10.dp)

                    var c1 = stringResource(R.string.winds)
                    var c2 = FormatUtil.printWindSpeed(
                        context,
                        isMetrics,
                        condPeriod.windDir,
                        Pair(condPeriod.windSpeedKPH, condPeriod.windSpeedMPH),
                    )
                    var c3 = stringResource(R.string.humidity)
                    var c4 = condPeriod.humidity?.let {
                        condPeriod.humidity.toInt().toString() + "%"
                    } ?: stringResource(R.string.na)
                    ComposeGrid(c1, c2, c3, c4)
                    c1 = stringResource(R.string.dew_point)

                    val tempStr =
                        FormatUtil.printDegree(
                            context,
                            isMetrics,
                            Pair(condPeriod.dewpointC, condPeriod.dewpointF),
                        )
                    c2 = tempStr
                    c3 = stringResource(R.string.pressure)
                    c4 = FormatUtil.printPressure(
                        context,
                        isMetrics,
                        Pair(condPeriod.pressureMB, condPeriod.pressureIN),
                    )
                    ComposeGrid(c1, c2, c3, c4)

                    val dayNight = DayNightPeriod(
                        fResponse.getPeriod(0),
                        fResponse.getPeriod(1),
                    )
                    ComposeFullDay(dayNight)
                    ComposeListCard(listResponse)
                }
            }
        }
    }

    @Composable
    private fun ComposeFullDay(period: DayNightPeriod) {
        Divider(color = Color.Black, thickness = 10.dp)

        Column(
            modifier = Modifier
                .padding(vertical = 5.dp, horizontal = 5.dp)
                .fillMaxWidth()
                .background(Color.DarkGray),
        ) {
            Row(
                modifier = Modifier
                    .padding(2.dp, 2.dp, 2.dp, 2.dp)
                    .fillMaxWidth()
                    .background(Color.DarkGray),
            ) {
                Column(
                    modifier = Modifier
                        .padding(5.dp, 5.dp, 5.dp, 0.dp)
                        .weight(1f),
                ) {
                    ComposeDayOrNight(period.day)
                }
                Column(
                    modifier = Modifier
                        .padding(5.dp, 5.dp, 5.dp, 0.dp)
                        .weight(1f),
                ) {
                    ComposeDayOrNight(period.night)
                }
            }
        }
    }

    @Composable
    private fun ComposeDayOrNight(period: ForecastPeriod) {
        val titleId = if (period.isDay) {
            R.string.today
        } else {
            R.string.tonight
        }
        Row(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 0.dp)
                .fillMaxWidth()
                .background(Color.DarkGray),
            Arrangement.Start,
        ) {
            Text(
                color = Color.White,
                text = stringResource(titleId),
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .padding(5.dp, 5.dp, 5.dp, 0.dp)
                    .widthIn(),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
        }
        Row(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 0.dp)
                .fillMaxWidth(),
            Arrangement.End,
        ) {
            Column() {
                period.let {
                    val minOrHigh =
                        if (it.isDay) {
                            Pair(it.maxTempC, it.maxTempF)
                        } else {
                            Pair(it.minTempC, it.minTempF)
                        }

                    val temp = FormatUtil.printDegree(context, isMetrics, minOrHigh)
                    Text(
                        color = Color.White,
                        text = temp,
                        textAlign = TextAlign.Right,

                        modifier = Modifier
                            .padding(0.dp, 15.dp, 5.dp, 0.dp)
                            .widthIn(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp,
                    )
                }

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
                        FileUtil.getDrawableByName(
                            period.icon,
                            context,
                        ),
                    ),
                    contentDescription = "",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier.height(60.dp),
                )
            }
        }
        Divider(color = Color(0xFF808080), thickness = 1.dp)

        Row(
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 0.dp)
                .fillMaxWidth(),
        ) {
            Text(
                color = Color.White,
                text = stringResource(R.string.winds),
                textAlign = TextAlign.Right,

                modifier = Modifier.widthIn(),
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
                        (period.windSpeedMinMPH.toInt() == period.windSpeedMaxMPH.toInt()) ->
                            FormatUtil.printWindSpeed(
                                context,
                                isMetrics,
                                period.windDir,
                                Pair(period.windSpeedMaxKPH, period.windSpeedMaxMPH),
                            )
                        else ->
                            FormatUtil.printWindSpeedMinMax(
                                context,
                                isMetrics,
                                period.windDir,
                                Pair(period.windSpeedMinKPH, period.windSpeedMinMPH),
                                Pair(period.windSpeedMaxKPH, period.windSpeedMaxMPH),
                            )
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
                .fillMaxWidth(),
        ) {
            val precipLabel: String?
            val precipTextView: String?
            if (period.snowIN == null || period.snowIN.toDouble() == 0.0) {
                precipLabel = stringResource(R.string.precip)
                precipTextView =
                    FormatUtil.printPrecip(
                        context,
                        isMetrics,
                        Pair(period.precipMM, period.precipIN),
                    )
            } else {
                precipLabel = stringResource(R.string.snow)
                precipTextView =
                    FormatUtil.printPrecip(context, isMetrics, Pair(period.snowCM, period.snowIN))
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

    @Composable
    private fun ComposePlace(place: PlacesResponse) {
        Row(
            modifier = Modifier
                .padding(8.dp, 8.dp, 8.dp, 8.dp)
                .fillMaxWidth(),
        ) {
            Text(
                color = Color.White,
                text = stringResource(R.string.currently),
                modifier = Modifier
                    .widthIn(),
            )
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd,
            ) {
                place.place?.let {
                    Text(
                        color = Color.White,
                        text = stringResource(
                            R.string.city_state_country,
                            it.name ?: it.city ?: stringResource(R.string.null_value),
                            it.state ?: stringResource(R.string.null_value),
                            it.country ?: stringResource(R.string.null_value),
                        ),
                        modifier = Modifier.widthIn(),
                    )
                } ?: Text(
                    color = Color.White,
                    text = stringResource(R.string.null_value),
                    modifier = Modifier.widthIn(),
                )
            }
        }
    }

    @Composable
    private fun ComposeCondition(cond: ConditionPeriod) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier
                    .padding(5.dp, 5.dp, 5.dp, 0.dp)
                    .weight(1f),
            ) {
                Box(
                    modifier = Modifier
                        .width(110.dp)
                        .height(110.dp)
                        .padding(10.dp, 5.dp, 5.dp, 5.dp),
                    Alignment.CenterStart,
                ) {
                    Image(
                        painter = painterResource(
                            FileUtil.getDrawableByName(
                                cond.icon,
                                context,
                            ),
                        ),
                        contentDescription = "",
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .height(110.dp)
                            .width(110.dp),
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(5.dp, 5.dp, 5.dp, 0.dp)
                    .weight(2f),
            ) {
                Row() {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd,
                    ) {
                        val tempStr =
                            FormatUtil.printDegree(context, isMetrics, Pair(cond.tempC, cond.tempF))

                        Text(
                            color = Color.White,
                            text = tempStr,
                            modifier = Modifier
                                .padding(5.dp, 5.dp, 0.dp, 5.dp)
                                .widthIn(),
                            fontSize = 34.sp,
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Divider(color = Color(0xFF808080), thickness = 1.dp)
                }

                Row() {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd,
                    ) {
                        Text(
                            color = Color.White,
                            text = cond.weatherPrimary,
                            modifier = Modifier
                                .padding(5.dp, 5.dp, 0.dp, 5.dp)
                                .widthIn(),
                        )
                    }
                }
                Row() {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd,
                    ) {
                        Text(
                            color = Color.White,
                            text = stringResource(
                                R.string.feels_like,
                                cond.weatherPrimary,
                            ),
                            modifier = Modifier
                                .padding(5.dp, 5.dp, 0.dp, 5.dp)
                                .widthIn(),
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun ComposeListCard(list: ForecastsResponse) {
        Divider(color = Color.Black, thickness = 10.dp)

        LazyVerticalGrid(
            contentPadding = PaddingValues(
                horizontal = 0.dp,
            ),
            columns = GridCells.Fixed(5),
            content = {
                items(5) { index ->
                    ComposeCard(list.periods[index])
                }
            },
        )
    }

    @Composable
    private fun ComposeCard(period: ForecastPeriod) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Gray),
            ) {
                Text(
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    text = WeatherUtil.getTimeFromISO(period.dateTimeISO, false),
                    modifier = Modifier.fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Box(
                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp)
                        .fillMaxWidth(),
                    Alignment.Center,
                ) {
                    Image(
                        painter = painterResource(
                            FileUtil.getDrawableByName(
                                period.icon,
                                context,
                            ),
                        ),
                        contentDescription = "",
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier.height(60.dp),
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Text(
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    text = period.tempF?.toString() ?: stringResource(R.string.na),
                    modifier = Modifier
                        .fillMaxWidth(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
            }
        }
    }
}
