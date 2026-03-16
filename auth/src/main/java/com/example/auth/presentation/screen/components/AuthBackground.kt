package com.example.auth.presentation.screen.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.auth.presentation.screen.components.style.AuthColors

@Composable
fun AuthBackgroundDecor() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
    ) {
        Box(
            modifier = Modifier
                .size(220.dp)
                .align(Alignment.TopEnd)
                .clip(MaterialTheme.shapes.extraLarge)
                .background(AuthColors.DecorTop)
        )
        Box(
            modifier = Modifier
                .width(180.dp)
                .height(120.dp)
                .align(Alignment.BottomStart)
                .clip(MaterialTheme.shapes.extraLarge)
                .background(AuthColors.DecorBottom)
        )
    }
}
