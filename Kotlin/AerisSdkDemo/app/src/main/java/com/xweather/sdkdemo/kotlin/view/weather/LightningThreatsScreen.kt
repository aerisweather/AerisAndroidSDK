package com.xweather.sdkdemo.kotlin.view.weather

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aerisweather.aeris.model.LightningFlashPeriod
import com.aerisweather.aeris.model.LightningThreatsResponse
import com.xweather.sdkdemo.kotlin.R
import com.xweather.sdkdemo.kotlin.data.weather.model.LightningThreatsData
import com.xweather.sdkdemo.kotlin.util.DayDateUtil
import com.xweather.sdkdemo.kotlin.view.weather.viewmodel.WeatherViewModel
import java.time.Duration
import java.time.ZonedDateTime

class LightningThreatsScreen(
    val context: Context,
    val isMetrics: Boolean?,
    val viewModel: WeatherViewModel,
) : IScreen {
    @RequiresApi(Build.VERSION_CODES.S)
    @Composable
    override fun Render(list: Any?) {
        (list as LightningThreatsData).apply {
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
                    ComposeSuccess(list)
                    ComposeError(list)

                    // just the 1st response
                    for (response in list.responses) {
                        ComposeResponse(response)

                        LazyColumn(
                            contentPadding = PaddingValues(
                                horizontal = 0.dp,
                                vertical = 0.dp,
                            ),
                        ) {
                            items(
                                items = response.periods,
                                itemContent = {
                                    ComposePeriod(it)
                                },
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun ComposeSuccess(threats: LightningThreatsData) {
        if (!threats.success) {
            Row(
                modifier = Modifier
                    .padding(2.dp, 2.dp, 2.dp, 2.dp)
                    .fillMaxWidth()
                    .background(Color.DarkGray),
            ) {
                Text(
                    color = Color.White,
                    text = stringResource(id = R.string.text_Success),
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .padding(5.dp, 5.dp, 5.dp, 0.dp)
                        .widthIn(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
                Text(
                    color = Color.White,
                    text = threats.success.toString(),
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .padding(5.dp, 5.dp, 5.dp, 0.dp)
                        .widthIn(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
            }
        }
    }

    @Composable
    private fun ComposeError(threats: LightningThreatsData) {
        threats.error?.code?.let {
            Row(
                modifier = Modifier
                    .padding(2.dp, 2.dp, 2.dp, 2.dp)
                    .fillMaxWidth()
                    .background(Color.DarkGray),
            ) {
                Text(
                    color = Color.White,
                    text = stringResource(id = R.string.text_result),
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .padding(5.dp, 5.dp, 5.dp, 0.dp)
                        .widthIn(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
                Text(
                    color = Color.White,
                    text = "${threats.error?.code} ${threats.error?.description}",
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .padding(5.dp, 5.dp, 5.dp, 0.dp)
                        .widthIn(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @Composable
    private fun ComposeResponse(response: LightningThreatsResponse) {
        Column(
            modifier = Modifier
                .background(Color.Black),
        ) {
            Row(
                modifier = Modifier
                    .background(Color.Gray)
                    .fillMaxWidth(),
            ) {
                Column(
                    Modifier
                        .fillMaxWidth(0.3f)
                        .padding(2.dp, 0.dp, 2.dp, 0.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = stringResource(id = R.string.text_id),
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .widthIn()
                            .padding(2.dp, 2.dp, 2.dp, 2.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(2.dp, 2.dp, 2.dp, 2.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = response.id,
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .widthIn(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                    )
                }
            }
            Row(
                modifier = Modifier
                    .background(Color.Gray)
                    .fillMaxWidth(),
            ) {
                Column(
                    Modifier
                        .fillMaxWidth(0.3f)
                        .padding(2.dp, 0.dp, 2.dp, 0.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = stringResource(id = R.string.text_datasource),
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .widthIn()
                            .padding(2.dp, 2.dp, 2.dp, 2.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(2.dp, 2.dp, 2.dp, 2.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = response.dataSource,
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .widthIn()
                            .padding(2.dp, 2.dp, 2.dp, 2.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                    )
                }
            }

            Row(
                modifier = Modifier
                    .background(Color.Gray)
                    .fillMaxWidth(),
            ) {
                Column(
                    Modifier
                        .fillMaxWidth(0.3f)
                        .padding(2.dp, 0.dp, 2.dp, 0.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = stringResource(id = R.string.text_profile),
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .widthIn()
                            .padding(2.dp, 2.dp, 2.dp, 2.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(2.dp, 2.dp, 2.dp, 2.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = response.profile.tz,
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .widthIn()
                            .padding(2.dp, 2.dp, 2.dp, 2.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                    )
                }
            }
            Row(
                modifier = Modifier
                    .background(Color.DarkGray)
                    .fillMaxWidth()
                    .padding(5.dp, 5.dp, 5.dp, 5.dp),
            ) {
                Text(
                    color = Color.White,
                    text = stringResource(id = R.string.text_detail),
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .padding(2.dp, 2.dp, 2.dp, 2.dp)
                        .widthIn(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                )
            }

            Row(
                modifier = Modifier
                    .background(Color.Gray)
                    .fillMaxWidth(),
            ) {
                Column(
                    Modifier
                        .fillMaxWidth(0.3f)
                        .padding(2.dp, 2.dp, 2.dp, 2.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = stringResource(id = R.string.text_stormId),
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .widthIn()
                            .padding(2.dp, 2.dp, 2.dp, 2.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(2.dp, 2.dp, 2.dp, 2.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = response.details.stormId,
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .widthIn(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                    )
                }
            }

            Row() {
                Column(
                    Modifier
                        .fillMaxWidth(0.3f)
                        .background(Color.Gray),
                ) {
                    val now = ZonedDateTime.now()
                    val zdt = DayDateUtil.convertZone(response.details.issuedDateTimeISO)
                    val diff = Duration.between(now, zdt)
                    val str = diff.let { "${-it.toMinutesPart()}m${-it.toSecondsPart()}s ago" }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(2.dp, 2.dp, 2.dp, 2.dp)
                            .background(Color.Gray),
                    ) {
                        Text(
                            color = Color.White,
                            text = str,
                            textAlign = TextAlign.Left,
                            modifier = Modifier
                                .padding(2.dp, 2.dp, 2.dp, 2.dp)
                                .widthIn(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(2.dp, 2.dp, 2.dp, 2.dp)
                            .fillMaxWidth(),
                    ) {
                        Text(
                            color = Color.White,
                            text = DayDateUtil.printTime(zdt),
                            textAlign = TextAlign.Left,
                            modifier = Modifier
                                .padding(2.dp, 2.dp, 2.dp, 2.dp)
                                .widthIn(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                        )
                    }
                }
                Column(
                    modifier = Modifier
                        .background(Color.Gray),
                ) {
                    Row(
                        modifier = Modifier
                            .padding(2.dp, 2.dp, 2.dp, 2.dp)
                            .fillMaxWidth(),
                    ) {
                        val speed = response.details.movement.let {
                            if (isMetrics == true) {
                                it.speedKPH
                            } else {
                                it.speedMPH
                            }
                        }
                        val unit = if (isMetrics == true) {
                            stringResource(id = R.string.text_kph)
                        } else {
                            stringResource(id = R.string.text_mph)
                        }
                        val speedStr = "$speed $unit"
                        Text(
                            color = Color.White,
                            text = "$speedStr ${response.details.movement.dir}",
                            textAlign = TextAlign.Left,
                            modifier = Modifier
                                .padding(2.dp, 2.dp, 2.dp, 2.dp)
                                .widthIn(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                        )
                    }
                    Row(
                        modifier = Modifier
                            .padding(2.dp, 2.dp, 2.dp, 2.dp)
                            .fillMaxWidth(),
                    ) {
                        Text(
                            color = Color.White,
                            text = "${stringResource(id = R.string.text_reliability)} ${response.details.movement.reliability}",
                            textAlign = TextAlign.Left,
                            modifier = Modifier
                                .padding(2.dp, 2.dp, 2.dp, 2.dp)
                                .widthIn(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .background(Color.DarkGray)
                    .fillMaxWidth()
                    .padding(5.dp, 5.dp, 5.dp, 5.dp),
            ) {
                Text(
                    color = Color.White,
                    text = stringResource(id = R.string.text_periods),
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .padding(5.dp, 5.dp, 5.dp, 0.dp)
                        .widthIn(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @Composable
    private fun ComposePeriod(period: LightningFlashPeriod) {
        Row(
            modifier = Modifier
                .background(Color.Black)
                .padding(2.dp, 2.dp, 2.dp, 2.dp),
        ) {
            Column(
                Modifier
                    .fillMaxWidth(0.5f)
                    .background(Color.DarkGray),
            ) {
                Row(
                    modifier = Modifier
                        .padding(2.dp, 2.dp, 2.dp, 2.dp)
                        .fillMaxWidth(),
                ) {
                    val minZdt = DayDateUtil.convertZone(period.range.minDateTimeISO)
                    val minTime = DayDateUtil.printTime(minZdt)

                    val maxZdt = DayDateUtil.convertZone(period.range.maxDateTimeISO)
                    val maxTime = DayDateUtil.printTime(maxZdt)
                    Text(
                        color = Color.White,
                        text = "$minTime - $maxTime",
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .padding(5.dp, 5.dp, 5.dp, 5.dp)
                            .widthIn(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                    )
                }
            }
            Column(
                modifier = Modifier
                    .background(Color.Gray),
            ) {
                Row(
                    modifier = Modifier
                        .padding(2.dp, 2.dp, 2.dp, 2.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(),
                ) {
                    Text(
                        color = Color.White,
                        text = "(${period.centroid.coordinates[0]},${period.centroid.coordinates[1]})",
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .padding(5.dp, 5.dp, 5.dp, 5.dp)
                            .widthIn(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                    )
                }
            }
        }
    }
}
