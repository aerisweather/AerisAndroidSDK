package com.xweather.sdkdemo.kotlin.view.weather

import android.content.Context
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
import com.aerisweather.aeris.model.LightningSummary
import com.aerisweather.aeris.model.LightningSummaryNumSensors
import com.aerisweather.aeris.model.LightningSummaryPeakAmp
import com.xweather.sdkdemo.kotlin.R
import com.xweather.sdkdemo.kotlin.data.weather.model.LightningSummaryData
import com.xweather.sdkdemo.kotlin.util.DayDateUtil
import com.xweather.sdkdemo.kotlin.view.ComposeGrid
import com.xweather.sdkdemo.kotlin.view.weather.viewmodel.WeatherViewModel

class LightningSummaryScreen(
    val context: Context,
    val isMetrics: Boolean?,
    val viewModel: WeatherViewModel,
) : IScreen {
    @Composable
    override fun Render(list: Any?) {
        (list as LightningSummaryData).apply {
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
                        .background(Color.Black),
                ) {
                    ComposeSuccess(list)
                    ComposeError(list)

                    // just the 1st response
                    LazyColumn(
                        contentPadding = PaddingValues(
                            horizontal = 0.dp,
                            vertical = 0.dp,
                        ),
                        modifier = Modifier.background(Color.DarkGray),
                    ) {
                        items(
                            items = list.responses,
                            itemContent = {
                                ComposeSummary(it.summary)
                            },
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun ComposeSuccess(threats: LightningSummaryData) {
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
    private fun ComposeError(threats: LightningSummaryData) {
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

    @Composable
    private fun ComposeSummary(summary: LightningSummary) {
        Row() {
            Column(Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .padding(5.dp, 5.dp, 5.dp, 5.dp)
                        .fillMaxWidth()
                        .background(Color.DarkGray),
                ) {
                    Text(
                        color = Color.White,
                        text = "Range",
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
                        .padding(2.dp, 0.dp, 2.dp, 0.dp)
                        .fillMaxWidth()
                        .background(Color.Gray),
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth(0.5f)
                            .padding(0.dp, 0.dp, 0.dp, 2.dp),
                    ) {
                        Text(
                            color = Color.White,
                            text = stringResource(id = R.string.text_from),
                            textAlign = TextAlign.Left,
                            modifier = Modifier
                                .padding(5.dp, 5.dp, 5.dp, 0.dp)
                                .widthIn(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                        )
                    }
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 0.dp, 0.dp, 2.dp),
                    ) {
                        val zdt = DayDateUtil.convertZone(summary.range.fromDateTimeISO)
                        Text(
                            color = Color.White,
                            text = DayDateUtil.printTime(zdt),
                            textAlign = TextAlign.Right,
                            modifier = Modifier
                                .padding(5.dp, 5.dp, 5.dp, 0.dp)
                                .widthIn(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .padding(2.dp, 0.dp, 2.dp, 0.dp)
                        .fillMaxWidth()
                        .background(Color.Gray),
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth(0.5f)
                            .padding(0.dp, 0.dp, 0.dp, 2.dp),
                    ) {
                        Text(
                            color = Color.White,
                            text = stringResource(id = R.string.text_to),
                            textAlign = TextAlign.Left,
                            modifier = Modifier
                                .padding(5.dp, 5.dp, 5.dp, 0.dp)
                                .widthIn(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                        )
                    }
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 0.dp, 0.dp, 2.dp),
                    ) {
                        val zdt = DayDateUtil.convertZone(summary.range.toDateTimeISO)
                        Text(
                            color = Color.White,
                            text = DayDateUtil.printTime(zdt),
                            textAlign = TextAlign.Right,
                            modifier = Modifier
                                .padding(5.dp, 5.dp, 5.dp, 0.dp)
                                .widthIn(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .padding(5.dp, 5.dp, 5.dp, 5.dp)
                        .fillMaxWidth()
                        .background(Color.DarkGray),
                ) {
                    Text(
                        color = Color.White,
                        text = stringResource(id = R.string.text_pulse),
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
                        .padding(2.dp, 0.dp, 2.dp, 0.dp)
                        .fillMaxWidth()
                        .background(Color.Gray),
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth(0.5f)
                            .padding(0.dp, 0.dp, 0.dp, 2.dp),
                    ) {
                        Text(
                            color = Color.White,
                            text = stringResource(id = R.string.text_count),
                            textAlign = TextAlign.Left,
                            modifier = Modifier
                                .padding(5.dp, 5.dp, 5.dp, 0.dp)
                                .widthIn(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                        )
                    }
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 0.dp, 0.dp, 2.dp),
                    ) {
                        Text(
                            color = Color.White,
                            text = "${summary.pulse.count}",
                            textAlign = TextAlign.Right,
                            modifier = Modifier
                                .padding(5.dp, 5.dp, 5.dp, 0.dp)
                                .widthIn(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .padding(2.dp, 0.dp, 2.dp, 0.dp)
                        .fillMaxWidth()
                        .background(Color.Gray),
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth(0.5f)
                            .padding(0.dp, 0.dp, 0.dp, 2.dp),
                    ) {
                        Text(
                            color = Color.White,
                            text = stringResource(id = R.string.text_cg),
                            textAlign = TextAlign.Left,
                            modifier = Modifier
                                .padding(5.dp, 5.dp, 5.dp, 0.dp)
                                .widthIn(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                        )
                    }
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 0.dp, 0.dp, 2.dp),
                    ) {
                        Text(
                            color = Color.White,
                            text = "${summary.pulse.cg}",
                            textAlign = TextAlign.Right,
                            modifier = Modifier
                                .padding(5.dp, 5.dp, 5.dp, 0.dp)
                                .widthIn(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(2.dp, 0.dp, 2.dp, 0.dp)
                        .fillMaxWidth()
                        .background(Color.Gray),
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth(0.5f)
                            .padding(0.dp, 0.dp, 0.dp, 2.dp),
                    ) {
                        Text(
                            color = Color.White,
                            text = stringResource(id = R.string.text_ic),
                            textAlign = TextAlign.Left,
                            modifier = Modifier
                                .padding(5.dp, 5.dp, 5.dp, 0.dp)
                                .widthIn(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                        )
                    }
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 0.dp, 0.dp, 2.dp),
                    ) {
                        Text(
                            color = Color.White,
                            text = "${summary.pulse.ic}",
                            textAlign = TextAlign.Right,
                            modifier = Modifier
                                .padding(5.dp, 5.dp, 5.dp, 0.dp)
                                .widthIn(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                        )
                    }
                }

                summary.pulse.positive?.let {
                    Row(
                        modifier = Modifier
                            .padding(2.dp, 0.dp, 2.dp, 0.dp)
                            .fillMaxWidth()
                            .background(Color.Gray),
                    ) {
                        Column(
                            Modifier
                                .fillMaxWidth(0.5f)
                                .padding(0.dp, 0.dp, 0.dp, 2.dp),
                        ) {
                            Text(
                                color = Color.White,
                                text = stringResource(id = R.string.text_positive),
                                textAlign = TextAlign.Left,
                                modifier = Modifier
                                    .padding(5.dp, 5.dp, 5.dp, 0.dp)
                                    .widthIn(),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                            )
                        }
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 0.dp, 0.dp, 2.dp),
                        ) {
                            Text(
                                color = Color.White,
                                text = "${summary.pulse.positive}",
                                textAlign = TextAlign.Right,
                                modifier = Modifier
                                    .padding(5.dp, 5.dp, 5.dp, 0.dp)
                                    .widthIn(),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                            )
                        }
                    }
                }

                summary.pulse.negative?.let {
                    Row(
                        modifier = Modifier
                            .padding(2.dp, 0.dp, 2.dp, 0.dp)
                            .fillMaxWidth()
                            .background(Color.Gray),
                    ) {
                        Column(
                            Modifier
                                .fillMaxWidth(0.5f)
                                .padding(0.dp, 0.dp, 0.dp, 2.dp),
                        ) {
                            Text(
                                color = Color.White,
                                text = stringResource(id = R.string.text_negative),
                                textAlign = TextAlign.Left,
                                modifier = Modifier
                                    .padding(5.dp, 5.dp, 5.dp, 0.dp)
                                    .widthIn(),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                            )
                        }
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 0.dp, 0.dp, 2.dp),
                        ) {
                            Text(
                                color = Color.White,
                                text = "$it",
                                textAlign = TextAlign.Right,
                                modifier = Modifier
                                    .padding(5.dp, 5.dp, 5.dp, 0.dp)
                                    .widthIn(),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                            )
                        }
                    }
                }
                summary.peakAmp?.let {
                    Row(
                        modifier = Modifier
                            .padding(5.dp, 5.dp, 5.dp, 5.dp)
                            .fillMaxWidth()
                            .background(Color.DarkGray),
                    ) {
                        Text(
                            color = Color.White,
                            text = stringResource(id = R.string.text_stats),
                            textAlign = TextAlign.Left,
                            modifier = Modifier
                                .padding(5.dp, 5.dp, 5.dp, 0.dp)
                                .widthIn(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                        )
                    }
                    ComposePeak(summary.peakAmp)
                    ComposeSensors(summary.numSensors)
                }
            }
        }
    }

    @Composable
    private fun ComposePeak(peak: LightningSummaryPeakAmp) {
        ComposeGrid(
            "",
            stringResource(R.string.text_min),
            stringResource(R.string.text_max),
            stringResource(R.string.text_avg),
        )
        peak.all.apply {
            ComposeGrid(
                stringResource(R.string.text_peak),
                formatPeakValue(min),
                formatPeakValue(max),
                formatPeakValue(avg),
            )
        }

        peak.positive.apply {
            ComposeGrid(
                stringResource(R.string.text_peak_pos),
                formatPeakValue(min),
                formatPeakValue(max),
                formatPeakValue(avg),
            )
        }

        peak.negative.apply {
            ComposeGrid(
                stringResource(R.string.text_peak_neg),
                formatPeakValue(min),
                formatPeakValue(max),
                formatPeakValue(avg),
            )
        }
    }

    @Composable
    private fun ComposeSensors(sensors: LightningSummaryNumSensors) {
        sensors.apply {
            ComposeGrid(
                stringResource(R.string.text_sensors),
                formatPeakValue(min),
                formatPeakValue(max),
                formatPeakValue(avg),
            )
        }
    }

    @Composable
    private fun formatPeakValue(num: Number?): String {
        return num?.let { "$it" + stringResource(R.string.text_ka) }
            ?: stringResource(R.string.text_not_avail)
    }
}
