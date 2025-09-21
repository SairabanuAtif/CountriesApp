package com.example.countriesapp.ui.components

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun LoadImage(
    url: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    isCircular: Boolean = false
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription,
        modifier = if (isCircular) {
            modifier.clip(CircleShape)
        } else {
            modifier
        },
        contentScale = ContentScale.Crop
    )
}
