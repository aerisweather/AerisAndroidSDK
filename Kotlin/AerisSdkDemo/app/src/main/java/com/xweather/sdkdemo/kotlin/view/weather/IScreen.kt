package com.xweather.sdkdemo.kotlin.view.weather

import androidx.compose.runtime.Composable

interface IScreen {

    @Composable
    fun Render(list: Any?)
}
