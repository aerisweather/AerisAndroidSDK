package com.example.demoaerisproject.view.locations

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.Observer
import com.aerisweather.aeris.response.PlacesResponse
import com.example.demoaerisproject.R
import com.example.demoaerisproject.data.room.MyPlace
import com.example.demoaerisproject.view.BaseActivity
import com.example.demoaerisproject.view.ComposeSnackbar
import com.example.demoaerisproject.view.ComposeSpinner
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class LocationSearchActivity : BaseActivity() {
    private val viewModel: LocationSearchViewModel by viewModels()
    private val DEBOUNCE_LIMIT = 1500L // seconds
    private var outlineTextValue: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        setContent {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.Black)
                    .testTag("init_black_box")
            )
        }
        actionBarTitle = resources.getString(R.string.activity_search)
        super.onCreate(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        viewModel.event.observe(this, Observer(::onViewModelEvent))
    }

    private fun onViewModelEvent(event: SearchEvent) {
        setContent {
            when (event) {
                is SearchEvent.Success -> {
                    Render(event)
                }
                is SearchEvent.InProgress -> {
                    Render()
                    ComposeSpinner()
                }
                is SearchEvent.Error -> {
                    Render()
                    ComposeSnackbar(event.msg)
                }
            }
        }
    }

    @Composable
    private fun Render(event: SearchEvent.Success? = null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 10.dp, 10.dp, 10.dp)
            ) {

                val focusRequester = remember { FocusRequester() }
                val editTextString = remember {
                    mutableStateOf(
                        TextFieldValue(
                            text = outlineTextValue,
                            selection = TextRange(outlineTextValue.length)
                        )
                    )
                }
                var timer: Timer? = null
                val debounce: (str: String) -> Unit = {
                    timer = Timer()
                    timer?.apply {
                        schedule(
                            object : TimerTask() {
                                override fun run() {
                                    viewModel.search(it)
                                    cancel()
                                }
                            },
                            DEBOUNCE_LIMIT, 5000
                        )
                    }
                }
                OutlinedTextField(
                    value = editTextString.value,
                    label = {
                        Text(
                            text = stringResource(id = R.string.text_input),
                            style = TextStyle(color = Color.Gray)
                        )
                    },

                    onValueChange = {
                        editTextString.value = it
                        if (outlineTextValue != it.text) {
                            timer?.cancel()
                            debounce(it.text)
                            outlineTextValue = it.text
                        }
                    },
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        textColor = Color.White,
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.Gray
                    ),
                    modifier = Modifier
                        .testTag("search_text")
                        .focusRequester(focusRequester)
                )
                LaunchedEffect(Unit) {
                    focusRequester.requestFocus()
                }

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    IconButton(onClick = {
                        viewModel.search(outlineTextValue)
                    }) {
                        Icon(
                            painterResource(id = R.drawable.ic_search),
                            contentDescription = resources.getString(R.string.activity_search),
                            tint = Color.White,
                            modifier = Modifier.padding(0.dp, 10.dp, 10.dp, 0.dp),
                        )
                    }
                }
            }

            if (event?.response?.isNotEmpty() == true) {
                LazyColumn(
                    contentPadding = PaddingValues(
                        horizontal = 16.dp, vertical = 8.dp
                    )
                ) {
                    items(
                        items = event.response,
                        itemContent = {
                            ComposeListPlace(place = it)
                        })
                }
            }
        }
    }

    @Composable
    private fun ComposeListPlace(place: PlacesResponse) {
        val openDialog = remember { mutableStateOf(false) }

        Card(
            Modifier
                .padding(horizontal = 5.dp, vertical = 5.dp)
                .fillMaxWidth()
                .background(Color.Black)
                .clickable {
                    openDialog.value = true
                },
            shape = RoundedCornerShape(corner = CornerSize(8.dp))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
            ) {
                place.place.apply {
                    Text(
                        text = resources.getString(
                            R.string.city_state_country,
                            name ?: city, state, country
                        ),
                        color = Color.White,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(10.dp)
                    )
                }

                Divider(color = Color(0xFF808080), thickness = 1.dp)
            }
        }

        if (openDialog.value) {
            AlertDialog(onDismissRequest = { openDialog.value = false },
                properties = DialogProperties(
                    dismissOnClickOutside = true,
                    dismissOnBackPress = true
                ),
                title = { Text(resources.getString(R.string.alert_dlg_confirm_title)) },
                text = { Text(resources.getString(R.string.alert_dlg_confirm_desc)) },
                confirmButton = {
                    Button(onClick = {
                        onDlgConfirm(place)
                        openDialog.value = false
                    }) {
                        Text(text = resources.getString(R.string.alert_dlg_yes))
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        openDialog.value = false
                    }) {
                        Text(text = resources.getString(R.string.alert_dlg_cancel))
                    }
                }
            )
        }
    }

    var onDlgConfirm: (myPlace: PlacesResponse?) -> Unit = {
        it?.apply {
            viewModel.addLocation(
                myPlace = MyPlace(
                    place.name ?: place.city,
                    place.state,
                    place.country,
                    true,
                    location.lat,
                    location.lon
                )
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuLocateMe -> {
                viewModel.locateMe()
                return true
            }
            R.id.menuMyLocs -> {
                startActivity(Intent(this, MyLocsActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}