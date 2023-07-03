package com.xweather.sdkdemo.kotlin.view.weather

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.aerisweather.aeris.model.AerisLocation
import com.aerisweather.aeris.model.MaritimePeriod
import com.aerisweather.aeris.model.MaritimeProfile
import com.xweather.sdkdemo.kotlin.R
import com.xweather.sdkdemo.kotlin.data.weather.model.MaritimeData
import com.xweather.sdkdemo.kotlin.util.DayDateUtil
import com.xweather.sdkdemo.kotlin.view.ComposeError
import com.xweather.sdkdemo.kotlin.view.weather.viewmodel.WeatherViewModel

class MaritimeScreen(
    val context: Context,
    val isMetrics: Boolean?,
    val viewModel: WeatherViewModel,
) : IScreen {
    @RequiresApi(Build.VERSION_CODES.S)
    @Composable
    override fun Render(maritimeData: Any?) {
        (maritimeData as MaritimeData).apply {
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
                    ComposeSuccess(maritimeData)
                    ComposeError(
                        maritimeData.error?.let { "${it.code} ${it.description}" }
                            ?: stringResource(
                                R.string.no_data
                            )
                    )
                    maritimeData.responses.get(0).let {
                        ComposeGeneral(it.location, it.interval, it.profile)

                        LazyColumn(
                            contentPadding = PaddingValues(
                                horizontal = 0.dp,
                                vertical = 0.dp,
                            ),
                        ) {
                            items(
                                items = it.periods.toList(),
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
    private fun ComposeGeneral(
        location: AerisLocation,
        interval: String,
        profile: MaritimeProfile,
    ) {
        Row() {
            Column(
                Modifier
                    .fillMaxWidth(0.5f)
                    .background(Color.Gray),
            ) {
                Text(
                    color = Color.White,
                    text = stringResource(id = R.string.text_location),
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .padding(5.dp, 5.dp, 5.dp, 5.dp)
                        .widthIn(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                )
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.Gray),
            ) {
                Text(
                    color = Color.White,
                    text = "(${location.lat} ${location.lon})",
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .padding(5.dp, 5.dp, 5.dp, 5.dp)
                        .widthIn(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                )
            }
        }

        Row() {
            Column(
                Modifier
                    .fillMaxWidth(0.5f)
                    .background(Color.Gray),
            ) {
                Text(
                    color = Color.White,
                    text = stringResource(id = R.string.text_interval),
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .padding(5.dp, 5.dp, 5.dp, 5.dp)
                        .widthIn(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                )
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.Gray),
            ) {
                Text(
                    color = Color.White,
                    text = interval,
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .padding(5.dp, 5.dp, 5.dp, 5.dp)
                        .widthIn(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                )
            }
        }

        Row() {
            Column(
                Modifier
                    .fillMaxWidth(0.5f)
                    .background(Color.Gray),
            ) {
                Text(
                    color = Color.White,
                    text = profile.tz,
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .padding(5.dp, 5.dp, 5.dp, 5.dp)
                        .widthIn(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                )
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.Gray),
            ) {
                Text(
                    color = Color.White,
                    text = if (isMetrics == true) {
                        "${profile.elevM} ${stringResource(id = R.string.unit_m)}"
                    } else {
                        "${profile.elevFT} ${stringResource(id = R.string.unit_ft)}"
                    },
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .padding(5.dp, 5.dp, 5.dp, 5.dp)
                        .widthIn(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.DarkGray),
        ) {
            Text(
                color = Color.White,
                text = stringResource(id = R.string.head_period),
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .padding(5.dp, 5.dp, 5.dp, 5.dp)
                    .widthIn(),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
        }
    }

    @Composable
    private fun ComposeDlg(period: MaritimePeriod) {
        val scrollState = rememberScrollState()
        // Draw a rectangle shape with rounded corners inside the dialog
        Column(
            Modifier
                .fillMaxSize()
                .background(Color.White)
                .verticalScroll(scrollState),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
            ) {
                IconButton(
                    onClick = { viewModel.openDialog.value = false },
                ) {
                    Icon(
                        painterResource(R.drawable.ic_close),
                        stringResource(id = R.string.close),
                        modifier = Modifier.size(28.dp),
                    )
                }
            }

            Row(
                modifier = Modifier
                    .padding(2.dp, 2.dp, 2.dp, 2.dp)
                    .fillMaxWidth()
                    .background(Color.Gray),
            ) {
                val zdt = DayDateUtil.convertZone(period.dateTimeISO)
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

            ComposeDlgSea(period)
            ComposeDlgPrimary(period)
            CompposeDlgSecondary(period)
            CompposeDlgTertiary(period)
        }
    }

    @Composable
    private fun ComposeDlgSea(period: MaritimePeriod) {
        Row(
            modifier = Modifier
                .padding(2.dp, 2.dp, 2.dp, 2.dp)
                .fillMaxWidth()
                .background(Color.DarkGray),
        ) {
            Text(
                color = Color.White,
                text = stringResource(id = R.string.head_sea_wind),
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
                .padding(2.dp, 2.dp, 2.dp, 2.dp)
                .fillMaxWidth()
                .background(Color.Gray),
        ) {
            Column(
                Modifier
                    .fillMaxWidth(0.5f)
                    .padding(0.dp, 0.dp, 0.dp, 2.dp)
                    .background(Color.Gray),
            ) {
                Text(
                    color = Color.White,
                    text = stringResource(id = R.string.text_surf_temp),
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .padding(5.dp, 5.dp, 5.dp, 0.dp)
                        .widthIn(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                )
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 0.dp, 2.dp)
                    .background(Color.Gray),
            ) {
                Text(
                    color = Color.White,
                    text = if (isMetrics == true) {
                        "${period.seaSurfaceTemperatureC} ${stringResource(id = R.string.unit_c)}"
                    } else {
                        "${period.seaSurfaceTemperatureF} ${stringResource(id = R.string.unit_f)}"
                    },
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .padding(5.dp, 5.dp, 5.dp, 0.dp)
                        .widthIn(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(2.dp, 2.dp, 2.dp, 2.dp)
                .fillMaxWidth()
                .background(Color.Gray),
        ) {
            Column(
                Modifier
                    .fillMaxWidth(0.5f)
                    .padding(0.dp, 0.dp, 0.dp, 2.dp)
                    .background(Color.Gray),
            ) {
                Text(
                    color = Color.White,
                    text = stringResource(id = R.string.text_speed),
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .padding(5.dp, 5.dp, 5.dp, 0.dp)
                        .widthIn(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                )
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 0.dp, 2.dp)
                    .background(Color.Gray),
            ) {
                Text(
                    color = Color.White,
                    text = if (isMetrics == true) {
                        "${period.seaCurrentSpeedKPH} ${stringResource(id = R.string.unit_kph)}"
                    } else {
                        "${period.seaCurrentSpeedMPH} ${stringResource(id = R.string.unit_kph)}"
                    },
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .padding(5.dp, 5.dp, 5.dp, 0.dp)
                        .widthIn(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(2.dp, 2.dp, 2.dp, 2.dp)
                .fillMaxWidth()
                .background(Color.Gray),
        ) {
            Column(
                Modifier
                    .fillMaxWidth(0.5f)
                    .padding(0.dp, 0.dp, 0.dp, 2.dp)
                    .background(Color.Gray),
            ) {
                Text(
                    color = Color.White,
                    text = stringResource(id = R.string.text_speed),
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .padding(5.dp, 5.dp, 5.dp, 0.dp)
                        .widthIn(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                )
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 0.dp, 2.dp)
                    .background(Color.Gray),
            ) {
                Text(
                    color = Color.White,
                    text = if (isMetrics == true) {
                        "${period.seaCurrentSpeedKTS} ${stringResource(id = R.string.unit_kts)}"
                    } else {
                        "${period.seaCurrentSpeedMPS} ${stringResource(id = R.string.unit_mps)}"
                    },
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .padding(5.dp, 5.dp, 5.dp, 0.dp)
                        .widthIn(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(2.dp, 2.dp, 2.dp, 2.dp)
                .fillMaxWidth()
                .background(Color.Gray),
        ) {
            Column(
                Modifier
                    .fillMaxWidth(0.5f)
                    .padding(0.dp, 0.dp, 0.dp, 2.dp)
                    .background(Color.Gray),
            ) {
                Text(
                    color = Color.White,
                    text = stringResource(id = R.string.text_sea_dir),
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .padding(5.dp, 5.dp, 5.dp, 0.dp)
                        .widthIn(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                )
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 0.dp, 2.dp)
                    .background(Color.Gray),
            ) {
                Text(
                    color = Color.White,
                    text = "${period.seaCurrentDir} ${period.seaCurrentDirDEG} degrees",
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .padding(5.dp, 5.dp, 5.dp, 0.dp)
                        .widthIn(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                )
            }
        }

        if (period.significantWaveHeightM != null && period.significantWaveHeightFT != null) {
            Row(
                modifier = Modifier
                    .padding(2.dp, 2.dp, 2.dp, 2.dp)
                    .fillMaxWidth()
                    .background(Color.Gray),
            ) {
                Column(
                    Modifier
                        .fillMaxWidth(0.5f)
                        .padding(0.dp, 0.dp, 0.dp, 2.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = stringResource(id = R.string.text_wave_height),
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .padding(5.dp, 5.dp, 5.dp, 0.dp)
                            .widthIn(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                    )
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 0.dp, 2.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = if (isMetrics == true) {
                            "${period.significantWaveHeightM} ${stringResource(id = R.string.unit_m)}"
                        } else {
                            "${period.significantWaveHeightFT} ${stringResource(id = R.string.unit_ft)}"
                        },
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

        if (period.tidesM != null && period.tidesFT != null) {
            Row(
                modifier = Modifier
                    .padding(2.dp, 2.dp, 2.dp, 2.dp)
                    .fillMaxWidth()
                    .background(Color.Gray),
            ) {
                Column(
                    Modifier
                        .fillMaxWidth(0.5f)
                        .padding(0.dp, 0.dp, 0.dp, 2.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = stringResource(id = R.string.text_tides),
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .padding(5.dp, 5.dp, 5.dp, 0.dp)
                            .widthIn(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                    )
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 0.dp, 2.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = if (isMetrics == true) {
                            "${period.tidesM} ${stringResource(id = R.string.unit_m)}"
                        } else {
                            "${period.tidesFT} ${stringResource(id = R.string.unit_ft)}"
                        },
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

        if (period.surgeM != null && period.surgeFT != null) {
            Row(
                modifier = Modifier
                    .padding(2.dp, 2.dp, 2.dp, 2.dp)
                    .fillMaxWidth()
                    .background(Color.Gray),
            ) {
                Column(
                    Modifier
                        .fillMaxWidth(0.5f)
                        .padding(0.dp, 0.dp, 0.dp, 2.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = stringResource(id = R.string.text_surge),
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .padding(5.dp, 5.dp, 5.dp, 0.dp)
                            .widthIn(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                    )
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 0.dp, 2.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = if (isMetrics == true) {
                            "${period.surgeM} ${stringResource(id = R.string.unit_m)}"
                        } else {
                            "${period.surgeFT} ${stringResource(id = R.string.unit_ft)}"
                        },
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

        period.windWavePeriod?.let {
            Row(
                modifier = Modifier
                    .padding(2.dp, 2.dp, 2.dp, 2.dp)
                    .fillMaxWidth()
                    .background(Color.Gray),
            ) {
                Column(
                    Modifier
                        .fillMaxWidth(0.5f)
                        .padding(0.dp, 0.dp, 0.dp, 2.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = stringResource(id = R.string.text_wind_wave_period),
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .padding(5.dp, 5.dp, 5.dp, 0.dp)
                            .widthIn(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                    )
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 0.dp, 2.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = "$it",
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

        if (period.windWaveDir != null || period.windWaveDirDEG != null) {
            Row(
                modifier = Modifier
                    .padding(2.dp, 2.dp, 2.dp, 2.dp)
                    .fillMaxWidth()
                    .background(Color.Gray),
            ) {
                Column(
                    Modifier
                        .fillMaxWidth(0.5f)
                        .padding(0.dp, 0.dp, 0.dp, 2.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = stringResource(id = R.string.text_wind_wave),
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .padding(5.dp, 5.dp, 5.dp, 0.dp)
                            .widthIn(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                    )
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 0.dp, 2.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = "${period.windWaveDir} ${period.windWaveDirDEG} degrees",
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

    @Composable
    private fun ComposeDlgPrimary(period: MaritimePeriod) {
        Row(
            modifier = Modifier
                .padding(2.dp, 2.dp, 2.dp, 2.dp)
                .fillMaxWidth()
                .background(Color.DarkGray),
        ) {
            Text(
                color = Color.White,
                text = stringResource(id = R.string.head_primary),
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .padding(5.dp, 5.dp, 5.dp, 0.dp)
                    .widthIn(),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
        }

        if (period.primaryWaveDir != null || period.primaryWaveDirDEG != null) {
            Row(
                modifier = Modifier
                    .padding(2.dp, 2.dp, 2.dp, 2.dp)
                    .fillMaxWidth()
                    .background(Color.Gray),
            ) {
                Column(
                    Modifier
                        .fillMaxWidth(0.5f)
                        .padding(0.dp, 0.dp, 0.dp, 2.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = stringResource(id = R.string.text_dir),
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .padding(5.dp, 5.dp, 5.dp, 0.dp)
                            .widthIn(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                    )
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 0.dp, 2.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = "${period.primaryWaveDir} ${period.primaryWaveDirDEG} degrees",
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

        period.primaryWavePeriod?.let {
            Row(
                modifier = Modifier
                    .padding(2.dp, 2.dp, 2.dp, 2.dp)
                    .fillMaxWidth()
                    .background(Color.Gray),
            ) {
                Column(
                    Modifier
                        .fillMaxWidth(0.5f)
                        .padding(0.dp, 0.dp, 0.dp, 2.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = stringResource(id = R.string.text_wave_period),
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .padding(5.dp, 5.dp, 5.dp, 0.dp)
                            .widthIn(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                    )
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 0.dp, 2.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = "$it",
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

        period.primarySwellPeriod?.let {
            Row(
                modifier = Modifier
                    .padding(2.dp, 2.dp, 2.dp, 2.dp)
                    .fillMaxWidth()
                    .background(Color.Gray),
            ) {
                Column(
                    Modifier
                        .fillMaxWidth(0.5f)
                        .padding(0.dp, 0.dp, 0.dp, 2.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = stringResource(id = R.string.text_swell_period),
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .padding(5.dp, 5.dp, 5.dp, 0.dp)
                            .widthIn(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                    )
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 0.dp, 2.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = "$it",
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

        if (period.primarySwellDir != null || period.primarySwellDirDEG != null) {
            Row(
                modifier = Modifier
                    .padding(2.dp, 2.dp, 2.dp, 2.dp)
                    .fillMaxWidth()
                    .background(Color.Gray),
            ) {
                Column(
                    Modifier
                        .fillMaxWidth(0.5f)
                        .padding(0.dp, 0.dp, 0.dp, 2.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = stringResource(id = R.string.text_swell_dir),
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .padding(5.dp, 5.dp, 5.dp, 0.dp)
                            .widthIn(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                    )
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 0.dp, 2.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = "${period.primarySwellDir} ${period.primarySwellDirDEG} degrees",
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

        if (period.primarySwellHeightM != null && period.primarySwellHeightFT != null) {
            Row(
                modifier = Modifier
                    .padding(2.dp, 2.dp, 2.dp, 2.dp)
                    .fillMaxWidth()
                    .background(Color.Gray),
            ) {
                Column(
                    Modifier
                        .fillMaxWidth(0.5f)
                        .padding(0.dp, 0.dp, 0.dp, 2.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = stringResource(id = R.string.text_swell_height),
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .padding(5.dp, 5.dp, 5.dp, 0.dp)
                            .widthIn(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                    )
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 0.dp, 2.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = if (isMetrics == true) {
                            "${period.primarySwellHeightM} ${stringResource(id = R.string.unit_m)}"
                        } else {
                            "${period.primarySwellHeightFT} ${stringResource(id = R.string.unit_ft)}"
                        },
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

    @Composable
    private fun CompposeDlgSecondary(period: MaritimePeriod) {
        Row(
            modifier = Modifier
                .padding(2.dp, 2.dp, 2.dp, 2.dp)
                .fillMaxWidth()
                .background(Color.DarkGray),
        ) {
            Text(
                color = Color.White,
                text = stringResource(id = R.string.head_secondary),
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .padding(5.dp, 5.dp, 5.dp, 0.dp)
                    .widthIn(),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
        }

        period.secondarySwellPeriod?.let {
            Row(
                modifier = Modifier
                    .padding(2.dp, 2.dp, 2.dp, 2.dp)
                    .fillMaxWidth()
                    .background(Color.Gray),
            ) {
                Column(
                    Modifier
                        .fillMaxWidth(0.5f)
                        .padding(0.dp, 0.dp, 0.dp, 2.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = stringResource(id = R.string.text_swell_period),
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .padding(5.dp, 5.dp, 5.dp, 0.dp)
                            .widthIn(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                    )
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 0.dp, 2.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = "$it",
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

        if (period.secondarySwellDir != null || period.secondarySwellDirDEG != null) {
            Row(
                modifier = Modifier
                    .padding(2.dp, 2.dp, 2.dp, 2.dp)
                    .fillMaxWidth()
                    .background(Color.Gray),
            ) {
                Column(
                    Modifier
                        .fillMaxWidth(0.5f)
                        .padding(0.dp, 0.dp, 0.dp, 2.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = stringResource(id = R.string.text_swell_dir),
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .padding(5.dp, 5.dp, 5.dp, 0.dp)
                            .widthIn(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                    )
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 0.dp, 2.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = "${period.secondarySwellDir} ${period.secondarySwellDirDEG} degrees",
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

        if (period.secondarySwellHeightM != null && period.secondarySwellHeightFT != null) {
            Row(
                modifier = Modifier
                    .padding(2.dp, 2.dp, 2.dp, 2.dp)
                    .fillMaxWidth()
                    .background(Color.Gray),
            ) {
                Column(
                    Modifier
                        .fillMaxWidth(0.5f)
                        .padding(0.dp, 0.dp, 0.dp, 2.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = stringResource(id = R.string.text_swell_height),
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .padding(5.dp, 5.dp, 5.dp, 0.dp)
                            .widthIn(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                    )
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 0.dp, 2.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = if (isMetrics == true) {
                            "${period.secondarySwellHeightM} ${stringResource(id = R.string.unit_m)}"
                        } else {
                            "${period.secondarySwellHeightFT} ${stringResource(id = R.string.unit_ft)}"
                        },
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

    @Composable
    private fun CompposeDlgTertiary(period: MaritimePeriod) {
        Row(
            modifier = Modifier
                .padding(2.dp, 2.dp, 2.dp, 2.dp)
                .fillMaxWidth()
                .background(Color.DarkGray),
        ) {
            Text(
                color = Color.White,
                text = stringResource(id = R.string.head_tertiary),
                textAlign = TextAlign.Left,
                modifier = Modifier
                    .padding(5.dp, 5.dp, 5.dp, 0.dp)
                    .widthIn(),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            )
        }

        period.tertiarySwellPeriod?.let {
            Row(
                modifier = Modifier
                    .padding(2.dp, 2.dp, 2.dp, 2.dp)
                    .fillMaxWidth()
                    .background(Color.Gray),
            ) {
                Column(
                    Modifier
                        .fillMaxWidth(0.5f)
                        .padding(0.dp, 0.dp, 0.dp, 2.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = stringResource(id = R.string.text_swell_period),
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .padding(5.dp, 5.dp, 5.dp, 0.dp)
                            .widthIn(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                    )
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 0.dp, 2.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = "$",
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

        if (period.tertiarySwellDir != null || period.tertiarySwellDirDEG != null) {
            Row(
                modifier = Modifier
                    .padding(2.dp, 2.dp, 2.dp, 2.dp)
                    .fillMaxWidth()
                    .background(Color.Gray),
            ) {
                Column(
                    Modifier
                        .fillMaxWidth(0.5f)
                        .padding(0.dp, 0.dp, 0.dp, 2.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = stringResource(id = R.string.text_swell_dir),
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .padding(5.dp, 5.dp, 5.dp, 0.dp)
                            .widthIn(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                    )
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 0.dp, 2.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = "${period.tertiarySwellDir} ${period.tertiarySwellDirDEG} degrees",
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

        if (period.tertiarySwellHeightM != null && period.tertiarySwellHeightFT != null) {
            Row(
                modifier = Modifier
                    .padding(2.dp, 2.dp, 2.dp, 2.dp)
                    .fillMaxWidth()
                    .background(Color.Gray),
            ) {
                Column(
                    Modifier
                        .fillMaxWidth(0.5f)
                        .padding(0.dp, 0.dp, 0.dp, 2.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = stringResource(id = R.string.text_swell_height),
                        textAlign = TextAlign.Left,
                        modifier = Modifier
                            .padding(5.dp, 5.dp, 5.dp, 0.dp)
                            .widthIn(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                    )
                }
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 0.dp, 2.dp)
                        .background(Color.Gray),
                ) {
                    Text(
                        color = Color.White,
                        text = if (isMetrics == true) {
                            "${period.tertiarySwellHeightM} ${stringResource(id = R.string.unit_m)}"
                        } else {
                            "${period.tertiarySwellHeightFT} ${stringResource(id = R.string.unit_ft)}"
                        },
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

    @Composable
    private fun ComposePeriod(period: MaritimePeriod) {
        var periodInFocus: MaritimePeriod?
        if (viewModel.openDialog.value) {
            Dialog(
                onDismissRequest = { viewModel.openDialog.value = false },
            ) {
                ComposeDlg(period = period)
            }
        }

        Row(
            modifier = Modifier
                .selectable(
                    selected = false,
                    onClick = {
                        viewModel.openDialog.value = true
                        periodInFocus = period
                    },
                ),
        ) {
            Column(
                Modifier
                    .fillMaxWidth(0.4f)
                    .padding(0.dp, 0.dp, 0.dp, 2.dp)
                    .background(Color.DarkGray),
            ) {
                val zdt = DayDateUtil.convertZone(period.dateTimeISO)
                Text(
                    color = Color.White,
                    text = DayDateUtil.printTime(zdt),
                    textAlign = TextAlign.Left,
                    modifier = Modifier
                        .padding(5.dp, 5.dp, 5.dp, 5.dp)
                        .widthIn(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                )
            }
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 0.dp, 2.dp)
                    .background(Color.Gray),
            ) {
                Text(
                    color = Color.White,
                    text = stringResource(id = R.string.text_surface) + if (isMetrics == true) {
                        " ${period.seaSurfaceTemperatureC} ${stringResource(id = R.string.unit_c)}"
                    } else {
                        " ${period.seaSurfaceTemperatureF} ${stringResource(id = R.string.unit_c)}"
                    },
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

    @Composable
    private fun ComposeSuccess(maritimeData: MaritimeData) {
        if (!maritimeData.success) {
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
                    text = maritimeData.success.toString(),
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
}
