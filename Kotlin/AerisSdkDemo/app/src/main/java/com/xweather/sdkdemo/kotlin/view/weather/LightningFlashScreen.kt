package com.xweather.sdkdemo.kotlin.view.weather

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.aerisweather.aeris.model.LightningFlashResponse
import com.xweather.sdkdemo.kotlin.R
import com.xweather.sdkdemo.kotlin.data.weather.model.LightningFlashData
import com.xweather.sdkdemo.kotlin.util.DayDateUtil
import com.xweather.sdkdemo.kotlin.view.weather.viewmodel.WeatherViewModel
import java.time.Duration
import java.time.ZonedDateTime

class LightningFlashScreen(
    val context: Context,
    val isMetrics: Boolean?,
    val viewModel: WeatherViewModel,
) : IScreen {
    @RequiresApi(Build.VERSION_CODES.S)
    @Composable
    override fun Render(list: Any?) {
        (list as LightningFlashData).apply {
            Card(
                Modifier
                    .fillMaxWidth()
                    .heightIn()
                    .padding(vertical = 0.dp, horizontal = 5.dp)
                    .background(Color.Black),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn()
                        .background(Color.Black),
                ) {
                    ComposeSuccess(list)
                    ComposeError(list)

                    LazyColumn() {
                        items(
                            items = list.responses.toList(),
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
    private fun ComposeSuccess(threats: LightningFlashData) {
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
    private fun ComposeError(threats: LightningFlashData) {
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
    private fun ComposeResponse(response: LightningFlashResponse) {
        Row(
            Modifier
                .padding(2.dp, 2.dp, 2.dp, 2.dp)
                .background(Color.Black),
        ) {
            Column(
                Modifier
                    .height(60.dp)
                    .fillMaxWidth(0.3f)
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
                    .height(60.dp)
                    .background(Color.Gray),
            ) {
                Row(
                    modifier = Modifier
                        .padding(2.dp, 0.dp, 2.dp, 2.dp)
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
                        "km"
                    } else {
                        "mi"
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
                        fontSize = 16.sp,
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(2.dp, 0.dp, 2.dp, 2.dp)
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
        }
    }
}
