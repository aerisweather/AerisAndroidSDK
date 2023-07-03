package com.xweather.sdkdemo.kotlin.view.weather

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aerisweather.aeris.model.AerisResponse
import com.aerisweather.aeris.model.Pollutant
import com.aerisweather.aeris.response.AirQualityResponse
import com.aerisweather.aeris.util.WeatherUtil
import com.xweather.sdkdemo.kotlin.R
import com.xweather.sdkdemo.kotlin.view.ComposeError

class AirQualityScreen(val context: Context, val isMetrics: Boolean?) : IScreen {
    @Composable
    override fun Render(list: Any?) {
        (list as? MutableList<*>?)?.get(0)?.apply {
            if ((this as AerisResponse).isSuccessfulWithResponses) {
                val aqResponses = ArrayList<AirQualityResponse>()
                for (data in listOfResponse) {
                    aqResponses.add(AirQualityResponse(data))
                }
                if (aqResponses.isNotEmpty()) {
                    aqResponses.get(0)?.id?.let {
                        LazyColumn(
                            contentPadding = PaddingValues(
                                horizontal = 0.dp,
                                vertical = 8.dp,
                            ),
                        ) {
                            items(
                                items = aqResponses,
                                itemContent = {
                                    ComposeListCard(airQuality = it)
                                },
                            )
                        }
                    }
                } else {
                    ComposeError(stringResource(R.string.no_data))
                }
            } else {
                ComposeError(stringResource(R.string.no_data))
            }
        }
    }

    @Composable
    private fun ComposeListCard(airQuality: AirQualityResponse) {
        Card(
            Modifier
                .padding(vertical = 5.dp)
                .fillMaxWidth()
                .background(Color.Black),
            shape = RoundedCornerShape(corner = CornerSize(6.dp)),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black),
                ) {
                    val str = airQuality.place?.name?.let { WeatherUtil.capitalize(it) }
                        ?: stringResource(R.string.null_value)
                    Text(
                        color = Color.White,
                        text = if (str.length > 11) {
                            stringResource(R.string.truncate, str.take(11))
                        } else {
                            str
                        },
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier
                            .padding(15.dp, 10.dp, 0.dp, 0.dp)
                            .widthIn(),
                    )
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd,
                    ) {
                        Text(
                            color = Color.White,
                            text = WeatherUtil.getFormatFromISO(
                                airQuality.periods.get(0).dateTimeISO,
                                "h:mm aa",
                            ),
                            textAlign = TextAlign.Right,
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            modifier = Modifier
                                .padding(15.dp, 15.dp, 0.dp, 0.dp)
                                .widthIn(),
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black),
                ) {
                    Box(
                        modifier = Modifier
                            .width(30.dp)
                            .height(30.dp),
                        Alignment.CenterStart,
                    ) {
                        Image(
                            painter = painterResource(
                                R.drawable.sunny,
                            ),
                            contentDescription = "",
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier.height(30.dp),
                        )
                    }
                    Text(
                        color = Color.White,
                        text = WeatherUtil.capitalize(airQuality.periods.get(0).category),
                        fontSize = 36.sp,
                        modifier = Modifier
                            .padding(0.dp, 11.dp, 15.dp, 0.dp)
                            .widthIn(),
                    )
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd,
                    ) {
                        Text(
                            color = Color.White,
                            text = airQuality.periods[0].aqi.toString(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 36.sp,
                            modifier = Modifier
                                .padding(0.dp, 11.dp, 15.dp, 0.dp)
                                .widthIn(),
                        )
                    }
                }
                Divider(color = Color(0xFF808080), thickness = 1.dp)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black),
                ) {
                    Text(
                        color = Color.White,
                        text = stringResource(
                            R.string.method_calculation,
                            WeatherUtil.capitalize(
                                airQuality.periods[0].method,
                            ),
                        ),
                        modifier = Modifier
                            .padding(15.dp, 5.dp, 0.dp, 5.dp)
                            .widthIn(),
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Black),
                ) {
                    Text(
                        color = Color.White,
                        text = stringResource(
                            R.string.dominant_pollutant,
                            WeatherUtil.capitalize(
                                airQuality.periods[0].dominant,
                            ),
                        ),
                        modifier = Modifier
                            .padding(15.dp, 5.dp, 0.dp, 5.dp)
                            .widthIn(),
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.DarkGray),
                ) {
                    Column {
                        for (pollutant in airQuality.periods.get(0).pollutants) {
                            ComposePollutant(pollutant)
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun ComposePollutant(pollutant: Pollutant) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp, 5.dp, 25.dp, 5.dp),
        ) {
            Text(
                text = WeatherUtil.capitalize(pollutant.name),
                color = Color.White,
                fontSize = 12.sp,
                modifier = Modifier.width(170.dp),
                textAlign = TextAlign.Left,
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .width(200.dp),
                contentAlignment = Alignment.CenterEnd,
            ) {
                Text(
                    text = WeatherUtil.capitalize(
                        pollutant.category ?: stringResource(R.string.null_value),
                    ),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    modifier = Modifier.width(130.dp),
                    textAlign = TextAlign.Left,
                )
                Text(
                    text = pollutant.aqi.toString(),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    modifier = Modifier.width(50.dp),
                    textAlign = TextAlign.Left,
                )
            }
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp, 5.dp, 25.dp, 5.dp),
        ) {
            Text(
                text = stringResource(R.string.ppb),
                color = Color.White,
                fontSize = 10.sp,
                modifier = Modifier.width(50.dp),
                textAlign = TextAlign.Left,
            )
            Text(
                text = pollutant.valuePPB?.toString() ?: stringResource(R.string.null_value),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                modifier = Modifier.width(50.dp),
                textAlign = TextAlign.Left,
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .width(200.dp),
                contentAlignment = Alignment.CenterEnd,
            ) {
                Text(
                    text = stringResource(R.string.ugm3),
                    color = Color.White,
                    fontSize = 12.sp,
                    modifier = Modifier.width(130.dp),
                    textAlign = TextAlign.Left,
                )
                Text(
                    text = pollutant.valueUGM3?.toString()
                        ?: stringResource(R.string.null_value),
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    modifier = Modifier.width(50.dp),
                    textAlign = TextAlign.Left,
                )
            }
        }
        Divider(color = Color(0xFF808080), thickness = 1.dp)
    }
}
