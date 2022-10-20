package com.example.demoaerisproject.view.weather

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.aerisweather.aeris.response.ObservationResponse
import com.aerisweather.aeris.util.FileUtil
import com.aerisweather.aeris.util.WeatherUtil
import com.example.demoaerisproject.util.FormatUtil

class NearbyObservationScreen(val context: Context, val isMetrics: Boolean?) : IScreen {
    @Composable
    override fun Render(list: Any?) {
        (list as MutableList<*>?)?.get(0)?.apply {
            if ((this as AerisResponse).isSuccessfulWithResponses) {
                val periods = ArrayList<ObservationResponse>()
                for (data in listOfResponse) {
                    periods.add(ObservationResponse(data))
                }
                if (isValidData(periods)) {
                    LazyColumn(
                        contentPadding = PaddingValues(
                            horizontal = 0.dp,
                            vertical = 8.dp
                        )
                    ) {
                        items(
                            items = periods,
                            itemContent = {
                                ComposeListCard(period = it)
                            })
                    }
                }
            }
        }
    }

    private fun isValidData(periods: ArrayList<ObservationResponse>): Boolean {
        return periods.get(0)?.place?.name?.isNotEmpty() == true
    }

    @Composable
    private fun ComposeListCard(period: ObservationResponse) {

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
                Text(
                    color = Color.White,
                    text = WeatherUtil.capitalize(period.place.name),
                    modifier = Modifier
                        .widthIn(),
                )
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        color = Color.White,
                        text = WeatherUtil.getFormatFromISO(
                            period.observation.dateTimeISO, "h:mm aa"
                        ),
                        modifier = Modifier
                            .widthIn(),
                    )
                }
            }

            Row(
                modifier = Modifier
                    .padding(5.dp, 5.dp, 5.dp, 0.dp)
                    .fillMaxWidth()
                    .background(Color.DarkGray)
            ) {
                Box(
                    modifier = Modifier
                        .width(50.dp)
                        .height(50.dp)
                        .padding(4.dp),
                    Alignment.CenterStart
                ) {
                    Image(
                        painter = painterResource(
                            FileUtil.getDrawableByName(
                                period.observation.icon,
                                context
                            )
                        ), contentDescription = "",
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier.height(50.dp)
                    )
                }
                Text(
                    color = Color.White,
                    text = period.observation.weatherShort,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(0.dp, 15.dp, 5.dp, 0.dp)
                        .widthIn()
                )
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    period.observation?.let {


                        val tempStr =
                            FormatUtil.printDegree(context, isMetrics, Pair(it.tempC, it.tempF))

                        Text(
                            color = Color.White,
                            text = tempStr,
                            fontWeight = FontWeight.Bold,
                            fontSize = 40.sp,
                            modifier = Modifier
                                .padding(0.dp, 0.dp, 5.dp, 0.dp)
                                .widthIn()
                        )
                    }
                }
            }
        }
    }
}