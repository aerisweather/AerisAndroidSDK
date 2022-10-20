package com.example.demoaerisproject.view.settings

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import com.example.demoaerisproject.view.BaseActivity
import com.example.demoaerisproject.R
import com.example.demoaerisproject.view.map.MyMapActivity
import com.example.demoaerisproject.view.weather.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsActivity : BaseActivity() {
    private val viewModel: PrefViewModel by viewModels()
    private val isEnabledNotification = mutableStateOf(false)
    private val isMetric = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        actionBarTitle = resources.getString(R.string.activity_search)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        viewModel.isNotificationEnabled.observe(this, Observer(::onNotificationEvent))
        viewModel.isMetric.observe(this, Observer(::onMetricEvent))
        render()
    }

    override fun onBackPressed() {
        val name = when (intent.getStringExtra("TAG")) {
            MyMapActivity.TAG.toString() -> MyMapActivity::class.java
            else -> MainActivity::class.java
        }
        val targetIntent = Intent(this, name)
        intent.getStringExtra("route")?.let {
            targetIntent.putExtra("route", it)
        }
        startActivity(targetIntent)
    }

    private fun onNotificationEvent(isEnabled: Boolean?) {
        when (isEnabled) {
            null -> {}
            else -> {
                isEnabledNotification.value = isEnabled
            }
        }
    }

    private fun onMetricEvent(isEnabled: Boolean?) {
        when (isEnabled) {
            null -> {}
            else -> {
                isMetric.value = isEnabled
            }
        }
    }

    private fun render() {
        setContent {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            )
            Card(
                Modifier
                    .padding(vertical = 5.dp)
                    .fillMaxWidth()
                    .background(Color.Black),
                shape = RoundedCornerShape(corner = CornerSize(6.dp))
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ComposePrefNotification(isEnabledNotification.value)
                    ComposeMetricImperial(isMetric.value)
                    Divider(color = Color(0xFF808080), thickness = 1.dp)
                }
            }
        }
    }

    @Composable
    private fun ComposeMetricImperial(isMetrics: Boolean) {
        Divider(color = Color(0xFF808080), thickness = 1.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
        ) {
            Text(
                color = Color.White,
                text = resources.getString(R.string.unit_measure_title),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(10.dp, 10.dp, 10.dp, 10.dp)
                    .fillMaxWidth(),
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
        ) {
            val radioOptions = listOf(
                resources.getString(R.string.radio_title_imperial),
                resources.getString(R.string.radio_title_metric)
            )
            val (selectedOption, onSelected) = remember {
                mutableStateOf(radioOptions[0])
            }

            val i = if (isMetrics) {
                1
            } else {
                0
            }
            onSelected(radioOptions.get(i))

            Column() {
                radioOptions.forEach { text ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .selectable(selected = text == selectedOption,
                                onClick = {
                                    onSelected(text)
                                    val isMetricsEnabled = text == radioOptions[1]
                                    viewModel.setMetric(isMetricsEnabled)
                                })
                    ) {
                        RadioButton(
                            selected = (text == selectedOption),
                            onClick = {
                                onSelected(text)
                                val isMetricsEnabled = text == radioOptions[1]
                                viewModel.setMetric(isMetricsEnabled)
                            },
                            colors = RadioButtonDefaults.colors(unselectedColor = Color.White)
                        )

                        Text(
                            text = text,
                            modifier = Modifier.padding(10.dp, 12.dp, 0.dp, 0.dp),
                            color = Color.White
                        )
                    }
                }
            }
        }
    }


    @Composable
    private fun ComposePrefNotification(isEnabled: Boolean) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
        ) {
            Text(
                color = Color.White,
                text = resources.getString(R.string.pref_cat_ntf_title),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(10.dp, 10.dp, 10.dp, 10.dp)
                    .fillMaxWidth(),
            )
        }
        Divider(color = Color(0xFF808080), thickness = 1.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
        ) {
            Text(
                color = Color.White,
                text = resources.getString(R.string.pref_ntf_enabled_title),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                modifier = Modifier
                    .padding(10.dp, 10.dp, 10.dp, 10.dp)
                    .fillMaxWidth(),
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
        ) {
            Column(
                modifier = Modifier
                    .weight(4f)
            ) {
                Text(
                    color = Color.White,
                    text = resources.getString(R.string.pref_ntf_enabled_sum),
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(10.dp, 10.dp, 10.dp, 10.dp)
                        .fillMaxWidth(),
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f),
                horizontalAlignment = Alignment.Start
            ) {
                Checkbox(
                    checked = isEnabled,
                    colors = CheckboxDefaults.colors(uncheckedColor = Color.White),
                    onCheckedChange = {
                        viewModel.setNotificationEnabled(it)
                    }, enabled = true
                )
            }
        }
    }
}