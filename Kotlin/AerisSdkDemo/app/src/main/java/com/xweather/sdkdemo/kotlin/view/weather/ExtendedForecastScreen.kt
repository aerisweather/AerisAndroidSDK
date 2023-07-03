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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aerisweather.aeris.model.AerisResponse
import com.aerisweather.aeris.model.ForecastPeriod
import com.aerisweather.aeris.response.ForecastsResponse
import com.aerisweather.aeris.util.FileUtil
import com.xweather.sdkdemo.kotlin.util.FormatUtil
import java.text.SimpleDateFormat
import java.util.*

class ExtendedForecastScreen(val context: Context, val isMetrics: Boolean?) : IScreen {

    @Composable
    override fun Render(list: Any?) {
        (list as MutableList<*>?)?.get(0)?.apply {
            if ((this as AerisResponse).isSuccessfulWithResponses) {
                val fResponse = ForecastsResponse(
                    firstResponse,
                )

                LazyColumn(
                    contentPadding = PaddingValues(
                        horizontal = 0.dp,
                        vertical = 8.dp,
                    ),
                ) {
                    items(
                        items = fResponse.periods,
                        itemContent = {
                            ComposeListCard(period = it)
                        },
                    )
                }
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

    @Composable
    private fun ComposeListCard(period: ForecastPeriod) {
        Column(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black),
            ) {
                val dayDate = parseDate(period)

                Column() {
                    Text(
                        color = Color.White,
                        text = dayDate.first,
                        modifier = Modifier
                            .padding(5.dp, 10.dp, 5.dp, 0.dp)
                            .widthIn(),
                    )
                    Text(
                        color = Color.White,
                        text = dayDate.second,
                        modifier = Modifier
                            .widthIn(),
                    )
                }
                Box(
                    modifier = Modifier
                        .width(80.dp)
                        .height(80.dp)
                        .padding(4.dp),
                    Alignment.CenterStart,
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
                        modifier = Modifier.height(80.dp),
                    )
                }
                Text(
                    color = Color.White,
                    text = period.weatherPrimary,
                    textAlign = TextAlign.Center,
                    fontSize = 14.sp,
                    modifier = Modifier
                        .padding(0.dp, 30.dp, 5.dp, 0.dp)
                        .width(100.dp),
                )
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd,
                ) {
                    Row() {
                        val tempStrMax =
                            FormatUtil.printDegree(
                                context,
                                isMetrics,
                                Pair(period.maxTempC, period.maxTempF),
                            )

                        Text(
                            color = Color.White,
                            text = tempStrMax,
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp,
                            modifier = Modifier
                                .padding(0.dp, 20.dp, 5.dp, 0.dp)
                                .widthIn(),
                        )

                        val tempStrMin =
                            FormatUtil.printDegree(
                                context,
                                isMetrics,
                                Pair(period.minTempC, period.minTempF),
                            )

                        Text(
                            color = Color.White,
                            text = tempStrMin,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .padding(10.dp, 20.dp, 5.dp, 0.dp)
                                .widthIn(),
                        )
                    }
                }
            }
        }
    }
}
