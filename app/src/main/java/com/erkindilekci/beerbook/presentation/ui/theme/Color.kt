package com.erkindilekci.beerbook.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val BeerColor = Color(0xFFE2A284)
val TopBarColor = Color(0xFFedc6b4)

val MaterialTheme.statusBarColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color.Black else BeerColor

val MaterialTheme.topBarColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color(0xFF0F0F0F) else TopBarColor