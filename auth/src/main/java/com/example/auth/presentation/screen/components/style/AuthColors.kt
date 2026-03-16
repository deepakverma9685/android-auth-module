package com.example.auth.presentation.screen.components.style

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

object AuthColors {
    val Accent = Color(0xFF8BB8A8)
    val AccentDark = Color(0xFF4C7A6D)
    val SurfaceTint = Color(0xFFF7F3F1)
    val Title = Color(0xFF334C46)
    val Subtitle = Color(0xFF6A857D)
    val Footer = Color(0xFF8EA4A0)
    val DecorTop = Color(0xFFFFE3E8).copy(alpha = 0.6f)
    val DecorBottom = Color(0xFFD7F0E8).copy(alpha = 0.7f)
    val BackgroundBrush = Brush.linearGradient(
        colors = listOf(Color(0xFFF8EEF2), Color(0xFFE7F1F0), Color(0xFFEAF2F8)),
        start = Offset(0f, 0f),
        end = Offset(1200f, 1800f)
    )
}
