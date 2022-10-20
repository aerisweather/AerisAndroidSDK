package com.example.demoaerisproject.view.weather

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aerisweather.aeris.model.AerisResponse
import com.aerisweather.aeris.model.ConditionPeriod
import com.aerisweather.aeris.model.ForecastPeriod
import com.aerisweather.aeris.response.ConditionsResponse
import com.aerisweather.aeris.response.ForecastsResponse
import com.aerisweather.aeris.response.PlacesResponse
import com.aerisweather.aeris.util.FileUtil
import com.example.demoaerisproject.R
import com.example.demoaerisproject.util.FormatUtil
import java.text.SimpleDateFormat
import java.util.*

class OverviewScreen(val context: Context, val isMetrics: Boolean?) : IScreen {
    @Composable
    override fun Render(list: Any?) {
        (list as MutableList<*>?)?.apply {
            if ((get(0) as AerisResponse).isSuccessfulWithResponses) {
                val condResponse = ConditionsResponse(
                    (get(0) as AerisResponse).firstResponse
                )
                val condPeriod = condResponse.getPeriod(0)
                val pResponse = PlacesResponse(
                    (get(1) as AerisResponse).firstResponse
                )
                val fResponse = ForecastsResponse(
                    (get(2) as AerisResponse).firstResponse
                )
                val periods = fResponse.periods

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.DarkGray)
                ) {
                    ComposePlace(pResponse)
                    ComposeCondition(condPeriod)
                    ComposeListCard(periods)
                }
            }
        }
    }

    @Composable
    private fun ComposePlace(place: PlacesResponse) {
        Row(
            modifier = Modifier
                .padding(8.dp, 8.dp, 8.dp, 8.dp)
                .fillMaxWidth()
        ) {
            Text(
                color = Color.White,
                text = stringResource(R.string.currently),
                modifier = Modifier
                    .widthIn(),
            )
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    color = Color.White,
                    text = stringResource(
                        R.string.city_state_country,
                        place.place.name ?: place.place.city,
                        place.place.state,
                        place.place.country
                    ),
                    modifier = Modifier
                        .widthIn(),
                )
            }
        }
    }

    @Composable
    private fun ComposeCondition(cond: ConditionPeriod) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(5.dp, 5.dp, 5.dp, 0.dp)
                    .weight(1f)
            ) {
                Box(
                    modifier = Modifier
                        .width(110.dp)
                        .height(110.dp)
                        .padding(10.dp, 5.dp, 5.dp, 5.dp),
                    Alignment.CenterStart
                ) {
                    Image(
                        painter = painterResource(
                            FileUtil.getDrawableByName(
                                cond.icon,
                                context
                            )
                        ), contentDescription = "",
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .height(110.dp)
                            .width(110.dp)
                    )
                }
            }
            Column(
                modifier = Modifier
                    .padding(5.dp, 5.dp, 5.dp, 0.dp)
                    .weight(2f)
            ) {
                Row() {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {

                        val tempStr =
                            FormatUtil.printDegree(context, isMetrics, Pair(cond.tempC, cond.tempF))

                        Text(
                            color = Color.White,
                            text = tempStr,
                            modifier = Modifier
                                .padding(5.dp, 5.dp, 0.dp, 5.dp)
                                .widthIn(),
                            fontSize = 34.sp
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Divider(color = Color(0xFF808080), thickness = 1.dp)
                }

                Row() {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
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
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            color = Color.White,
                            text = stringResource(R.string.feels_like, cond.weatherPrimary),
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
    private fun ComposeListCard(list: List<ForecastPeriod>) {
        LazyColumn(
            contentPadding = PaddingValues(
                horizontal = 0.dp,
                vertical = 8.dp
            )
        ) {
            items(
                items = list,
                itemContent = {
                    ComposeCard(period = it)
                })
        }
    }

    @Composable
    private fun ComposeCard(period: ForecastPeriod) {
        Card(
            Modifier
                .fillMaxWidth()
                .background(Color.Black),
            shape = RoundedCornerShape(corner = CornerSize(6.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
            ) {
                Row(
                    modifier = Modifier
                        .padding(5.dp, 5.dp, 5.dp, 0.dp)
                        .fillMaxWidth()
                        .background(Color.Black)
                ) {
                    val formatter = SimpleDateFormat(
                        "yyyy-MM-dd",
                        Locale.ENGLISH
                    )
                    val dateObj: Date =
                        formatter.parse(period.dateTimeISO.split("T").toTypedArray()[0]) as Date
                    val dayFormat = SimpleDateFormat(
                        "EEE",
                        Locale.ENGLISH
                    )
                    val monthFormat = SimpleDateFormat(
                        "MMM dd",
                        Locale.ENGLISH
                    )
                    Column(
                        modifier = Modifier
                            .widthIn()
                            .background(Color.DarkGray)
                    ) {
                        Text(
                            color = Color.White,
                            text = dayFormat.format(dateObj),
                            modifier = Modifier
                                .widthIn(),
                        )
                        Text(
                            color = Color.White,
                            text = monthFormat.format(dateObj),
                            modifier = Modifier
                                .widthIn(),
                        )
                    }
                    Box(
                        modifier = Modifier
                            .width(50.dp)
                            .height(50.dp)
                            .padding(5.dp),
                        Alignment.CenterStart
                    ) {
                        Image(
                            painter = painterResource(
                                FileUtil.getDrawableByName(
                                    period.icon,
                                    context
                                )
                            ), contentDescription = "",
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier.height(50.dp)
                        )
                    }
                    Text(
                        color = Color.White,
                        text = period.weatherPrimary,
                        modifier = Modifier
                            .widthIn()
                            .padding(0.dp, 10.dp, 5.dp, 0.dp),
                        fontSize = 16.sp
                    )
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Row(
                            modifier = Modifier
                        ) {

                            val maxTempStr =
                                FormatUtil.printDegree(
                                    context,
                                    isMetrics,
                                    Pair(period.maxTempC, period.maxTempF)
                                )

                            Text(
                                color = Color.White,
                                text = maxTempStr,
                                modifier = Modifier
                                    .widthIn()
                                    .padding(0.dp, 0.dp, 5.dp, 0.dp),
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold
                            )

                            val minTempStr =
                                FormatUtil.printDegree(
                                    context,
                                    isMetrics,
                                    Pair(period.minTempC, period.minTempF)
                                )

                            Text(
                                color = Color.White,
                                text = minTempStr,
                                modifier = Modifier
                                    .widthIn()
                                    .padding(30.dp, 0.dp, 5.dp, 0.dp),
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                Divider(color = Color(0xFF808080), thickness = 1.dp)
            }
        }
    }
}