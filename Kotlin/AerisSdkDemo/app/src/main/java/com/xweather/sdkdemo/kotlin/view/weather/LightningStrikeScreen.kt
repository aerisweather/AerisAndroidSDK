package com.xweather.sdkdemo.kotlin.view.weather

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import com.aerisweather.aeris.model.LightningStrikeResponse
import com.xweather.sdkdemo.kotlin.R
import com.xweather.sdkdemo.kotlin.data.weather.model.LightningData
import com.xweather.sdkdemo.kotlin.util.DayDateUtil
import com.xweather.sdkdemo.kotlin.view.ComposeError
import com.xweather.sdkdemo.kotlin.view.weather.viewmodel.WeatherViewModel
import java.time.Duration
import java.time.ZonedDateTime

class LightningStrikeScreen(
    val context: Context,
    val isMetrics: Boolean?,
    val viewModel: WeatherViewModel,
) : IScreen {
    @RequiresApi(Build.VERSION_CODES.S)
    @Composable
    override fun Render(list: Any?) {
        (list as LightningData).apply {
            Card(
                Modifier
                    .fillMaxWidth()
                    .heightIn()
                    .padding(vertical = 2.dp, horizontal = 5.dp)
                    .background(Color.Black),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn()
                        .background(Color.Black),
                ) {
                    ComposeSuccess(list)
                    ComposeError(list.error?.let { "${it.code} ${it.description}" }
                        ?: stringResource(R.string.no_data))

                    LazyColumn(
                        contentPadding = PaddingValues(
                            horizontal = 0.dp,
                            vertical = 0.dp,
                        ),
                    ) {
                        items(
                            items = list.list,
                            itemContent = {
                                ComposeResponse(response = it)
                            },
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun ComposeSuccess(threats: LightningData) {
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

    @RequiresApi(Build.VERSION_CODES.S)
    @Composable
    private fun ComposeResponse(response: LightningStrikeResponse) {
        Row() {
            Column(
                Modifier
                    .fillMaxWidth(0.3f)
                    .padding(0.dp, 0.dp, 0.dp, 2.dp)
                    .background(Color.DarkGray),
            ) {
                val now = ZonedDateTime.now()
                val zdt = DayDateUtil.convertZone(response.ob.dateTimeISO)
                val diff = Duration.between(now, zdt)
                val str = diff.let { "${-it.toMinutesPart()}:${-it.toSecondsPart()} ago" }
                Row(
                    modifier = Modifier
                        .padding(2.dp, 2.dp, 2.dp, 2.dp)
                        .fillMaxWidth(),
                ) {
                    Text(
                        color = Color.White,
                        text = str,
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .padding(5.dp, 5.dp, 5.dp, 0.dp)
                            .widthIn(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
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
                            .padding(5.dp, 5.dp, 5.dp, 0.dp)
                            .widthIn(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(0.dp, 0.dp, 0.dp, 2.dp)
                    .background(Color.Gray),
            ) {
                Row(
                    modifier = Modifier
                        .padding(2.dp, 2.dp, 2.dp, 2.dp)
                        .fillMaxWidth(),
                ) {
                    val distance = response.relativeTo.let {
                        if (isMetrics == true) {
                            it.distanceKM
                        } else {
                            it.distanceMI
                        }
                    }
                    val unit = if (isMetrics == true) {
                        stringResource(id = R.string.text_km)
                    } else {
                        stringResource(id = R.string.text_mi)
                    }
                    val distanceStr = "$distance $unit"
                    Text(
                        color = Color.White,
                        text = "$distanceStr ${response.relativeTo.bearingENG}",
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .padding(5.dp, 5.dp, 5.dp, 0.dp)
                            .widthIn(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(2.dp, 2.dp, 2.dp, 2.dp)
                        .fillMaxWidth(),
                ) {
                    Text(
                        color = Color.White,
                        text = "(${response.loc.lat} ${response.loc.lon})",
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .padding(5.dp, 5.dp, 5.dp, 0.dp)
                            .widthIn(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 0.dp, 2.dp)
                    .background(Color.DarkGray),
            ) {
                Row(
                    modifier = Modifier
                        .padding(2.dp, 2.dp, 2.dp, 2.dp)
                        .fillMaxWidth(),
                ) {
                    val type = getPulseType(response.ob.pulse.type)
                    Text(
                        color = Color.White,
                        text = type,
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .padding(5.dp, 5.dp, 5.dp, 0.dp)
                            .widthIn(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(2.dp, 2.dp, 2.dp, 2.dp)
                        .fillMaxWidth(),
                ) {
                    Text(
                        color = Color.White,
                        text = "sensors:${response.ob.pulse.numSensors}",
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .padding(5.dp, 5.dp, 5.dp, 0.dp)
                            .widthIn(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                    )
                }
            }
        }
    }

    private fun getPulseType(type: String): String {
        return when (type) {
            "ic" -> "cloud-to-cloud"
            "cg" -> "cloud-to-ground"
            else -> type
        }
    }
}
