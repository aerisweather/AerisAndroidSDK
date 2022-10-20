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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aerisweather.aeris.model.Moon
import com.aerisweather.aeris.model.Sun
import com.aerisweather.aeris.util.WeatherUtil
import com.example.demoaerisproject.R
import com.example.demoaerisproject.data.weather.model.SunMoon
import com.example.demoaerisproject.util.DayDateUtil

class SunMoonScreen(val context: Context, val isMetrics: Boolean?) : IScreen {
    @Composable
    override fun Render(list: Any?) {
        (list as List<*>?)?.let {
            LazyColumn(
                contentPadding = PaddingValues(
                    horizontal = 0.dp,
                    vertical = 8.dp
                )
            ) {
                items(
                    items = it,
                    itemContent = {
                        ComposeListCard(sunmoon = it as SunMoon)
                    })
            }
        }
    }

    @Composable
    private fun ComposeListCard(sunmoon: SunMoon) {
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
                sunmoon.dateTimeISO?.apply {
                    val str = when {
                        DayDateUtil.isToday(this) -> stringResource(R.string.today)
                        DayDateUtil.isTomorrow(this) -> stringResource(R.string.tomorrow)
                        else -> WeatherUtil.getFormatFromISO(this, "EEE dd")
                    }
                    Text(
                        color = Color.White,
                        text = str,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(5.dp, 5.dp, 5.dp, 0.dp)
                        .fillMaxWidth()
                        .background(Color.Black)
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        ComposeSun(sunmoon.sun)
                    }
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.End
                    ) {
                        ComposeMoon(sunmoon.moon)
                    }
                }
                Row(
                    modifier = Modifier
                        .padding(5.dp, 5.dp, 5.dp, 5.dp)
                        .fillMaxWidth()
                        .background(Color.Black)
                ) {
                    Text(
                        color = Color.White,
                        text = stringResource(R.string.moon_phase),
                        modifier = Modifier
                            .widthIn(),
                    )
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        Text(
                            color = Color.White,
                            text = WeatherUtil.capitalize(sunmoon.moon?.phase?.name),
                            modifier = Modifier
                                .widthIn(),
                        )
                    }
                }
                Divider(color = Color(0xFF808080), thickness = 1.dp)
            }
        }
    }

    @Composable
    private fun ComposeSun(model: Sun?) {
        Row(
            modifier = Modifier
                .padding(5.dp, 5.dp, 5.dp, 0.dp)
                .fillMaxWidth()
                .background(Color.Black)
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
                        R.drawable.sunny
                    ), contentDescription = "",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier.height(50.dp)
                )
            }
            Text(
                color = Color.White,
                text = WeatherUtil.getFormatFromISO(model?.setISO, "h:mm a"),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(0.dp, 15.dp, 5.dp, 0.dp)
                    .widthIn(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            modifier = Modifier
                .padding(5.dp, 5.dp, 5.dp, 0.dp)
                .fillMaxWidth()
                .background(Color.Black)
        ) {
            Text(
                color = Color.White,
                text = stringResource(R.string.total_daylight),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(0.dp, 5.dp, 5.dp, 0.dp)
                    .widthIn()
            )
        }
        /*
        * TODO implement Gradient custom_sunmoon_pbar
        *  <gradient
                    android:startColor="#FFDB58"
                    android:centerColor="#EAC117"
                    android:endColor="#FBB117"
                    android:angle="180"
                />
        */
        Row(
            modifier = Modifier
                .padding(5.dp, 5.dp, 0.dp, 5.dp)
                .fillMaxWidth()
                .background(Color(0xFFEAC117)),

            ) {
            val rise = model?.rise?.toLong() ?: 0L
            val set = model?.set?.toLong() ?: 0L
            val seconds: Long = set - rise
            val hours = seconds / 60 / 60
            val minutes = seconds / 60 - hours * 60
            val text =
                if (model?.riseISO != null && model.setISO != null) {
                    stringResource(
                        R.string.total_dalight_format,
                        hours,
                        minutes
                    )
                } else {
                    stringResource(R.string.na)
                }
            Text(
                color = Color.White,
                text = text,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .widthIn()
            )
        }
    }

    @Composable
    private fun ComposeMoon(model: Moon?) {
        Row(
            modifier = Modifier
                .padding(5.dp, 5.dp, 5.dp, 0.dp)
                .fillMaxWidth()
                .background(Color.Black),
            horizontalArrangement = Arrangement.End
        ) {

            Text(
                color = Color.White,
                text = model?.setISO?.let {
                    WeatherUtil.getFormatFromISO(it, "h:mm a")
                }
                    ?: stringResource(R.string.null_value),
                modifier = Modifier
                    .padding(0.dp, 15.dp, 5.dp, 0.dp)
                    .widthIn(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
            Box(
                modifier = Modifier
                    .width(50.dp)
                    .height(50.dp)
                    .padding(4.dp),
                Alignment.CenterEnd
            ) {
                Image(
                    painter = painterResource(
                        R.drawable.sunnyn
                    ), contentDescription = "",
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier.height(50.dp)
                )
            }
        }
        Row(
            modifier = Modifier
                .padding(5.dp, 5.dp, 5.dp, 0.dp)
                .fillMaxWidth()
                .background(Color.Black),
            horizontalArrangement = Arrangement.End

        ) {
            Text(
                color = Color.White,
                text = " ",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(0.dp, 5.dp, 5.dp, 0.dp)
                    .widthIn()
            )
        }
        Row(
            modifier = Modifier
                .padding(5.dp, 5.dp, 5.dp, 0.dp)
                .fillMaxWidth()
                .background(Color(0xFF002849)),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                color = Color.White,
                text = " ",
                textAlign = TextAlign.Center,
                modifier = Modifier
            )
        }
    }
}