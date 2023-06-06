package com.erkindilekci.beerbook.presentation.listscreen

import android.annotation.SuppressLint
import android.graphics.Color.parseColor
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.erkindilekci.beerbook.domain.model.Beer
import com.erkindilekci.beerbook.util.PaletteGenerator.convertImageUrlToBitmap
import com.erkindilekci.beerbook.util.PaletteGenerator.extractColorsFromBitmap
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ListScreenItem(
    beer: Beer,
    modifier: Modifier = Modifier,
    viewModel: ListScreenViewModel = hiltViewModel()
) {
    val colorPalette by viewModel.colorPalette
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var vibrant by remember { mutableStateOf("#000000") }
    var darkVibrant by remember { mutableStateOf("#0F0F0F") }
    var onDarkVibrant by remember { mutableStateOf("#FFFFFF") }

    LaunchedEffect(key1 = true) {
        val bitmap = beer.imageUrl?.let {
            convertImageUrlToBitmap(
                imageUrl = it,
                context = context
            )
        }
        if (bitmap != null) {
            viewModel.setColorPalette(
                colors = extractColorsFromBitmap(bitmap)
            )
        }
    }

    scope.launch {
        vibrant = colorPalette["vibrant"] ?: "#000000"
        darkVibrant = colorPalette["darkVibrant"] ?: "#0F0F0F"
        onDarkVibrant = colorPalette["onDarkVibrant"] ?: "#CCCCCC"
    }

    Card(modifier = modifier, elevation = CardDefaults.cardElevation(4.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(parseColor(darkVibrant)))
                .height(IntrinsicSize.Max)
                .padding(16.dp)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(beer.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = beer.name,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .weight(1f)
                    .height(180.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier
                    .weight(3f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = beer.name,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(parseColor(onDarkVibrant))
                )

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = beer.tagline,
                    fontStyle = FontStyle.Italic,
                    fontSize = 15.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(0.7f)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = beer.description,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Justify,
                    color = Color(parseColor(onDarkVibrant))
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "First brewed in ${beer.firstBrewed}",
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End
                )
            }
        }
    }
}
