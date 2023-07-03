package com.xweather.sdkdemo.kotlin.view.locations

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import com.xweather.sdkdemo.kotlin.R
import com.xweather.sdkdemo.kotlin.data.room.MyPlace
import com.xweather.sdkdemo.kotlin.view.BaseActivity
import com.xweather.sdkdemo.kotlin.view.ComposeSnackbar
import com.xweather.sdkdemo.kotlin.view.ComposeSpinner
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyLocsActivity : BaseActivity() {
    private val viewModel: MyLocationViewModel by viewModels<MyLocationViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        actionBarTitle = resources.getString(R.string.activity_my_locs)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        viewModel.event.observe(this, Observer(::onViewModelEvent))
    }

    private fun onViewModelEvent(event: MyLocationEvent) {
        setContent {
            when (event) {
                is MyLocationEvent.Success -> Render(event)
                is MyLocationEvent.InProgress -> ComposeSpinner()
                is MyLocationEvent.Error -> ComposeSnackbar(event.msg)
            }
        }
    }

    @Composable
    private fun Render(event: MyLocationEvent.Success? = null) {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.Black),
        )
        event?.let {
            if (it.response.isNotEmpty()) {
                LazyColumn(
                    contentPadding = PaddingValues(
                        horizontal = 16.dp,
                        vertical = 8.dp,
                    ),
                ) {
                    items(
                        items = event.response.sortedWith(
                            compareBy<MyPlace> {
                                it.country
                            }.thenBy { it.state }
                                .thenBy { it.name },
                        ),
                        itemContent = {
                            ComposeMyLocation(it)
                        },
                    )
                }
                return
            }
        }
        ComposeDefault()
    }

    @Composable
    private fun ComposeDefault() {
        Card(
            Modifier
                .fillMaxSize()
                .background(Color.Black),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black),
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(5.dp, 5.dp, 5.dp, 5.dp),
                ) {
                    Text(
                        color = Color.White,
                        text = resources.getString(R.string.no_locations_saved),
                        modifier = Modifier.padding(5.dp),
                    )
                }
            }
        }
    }

    @Composable
    private fun ComposeMyLocation(myPlace: MyPlace) {
        Card(
            Modifier
                .padding(horizontal = 5.dp, vertical = 5.dp)
                .fillMaxWidth()
                .clickable {
                    onClickPlaceCard(myPlace)
                },
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                myPlace.apply {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .background(Color.Black),
                    ) {
                        RadioButton(
                            selected = myLoc,
                            onClick = { onClickPlaceCard(myPlace) },
                            modifier = Modifier.padding(5.dp),
                        )
                        Text(
                            text = resources.getString(
                                R.string.city_state_country,
                                name,
                                state,
                                country,
                            ),
                            fontSize = 18.sp,
                            color = Color.White,
                            modifier = Modifier.padding(15.dp),
                            textAlign = TextAlign.Center,
                        )
                    }
                    Divider(color = Color(0xFF808080), thickness = 1.dp)
                }
            }
        }
    }

    var onClickPlaceCard: (place: MyPlace) -> Unit = {
        viewModel.selectAsMyLocation(it)
    }
}
