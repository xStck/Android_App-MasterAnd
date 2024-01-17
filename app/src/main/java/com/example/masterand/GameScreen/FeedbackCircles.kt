package com.example.masterand.GameScreen

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FeedbackCircles(colors: List<Color>) {
    val colorAnimation0 = remember { Animatable(Color.Transparent) }
    val colorAnimation1 = remember { Animatable(Color.Transparent) }
    val colorAnimation2 = remember { Animatable(Color.Transparent) }
    val colorAnimation3 = remember { Animatable(Color.Transparent) }
    LaunchedEffect(colors) {
        colorAnimation0.animateTo(colors[0], animationSpec = tween(300))
        colorAnimation1.animateTo(colors[1], animationSpec = tween(300))
        colorAnimation2.animateTo(colors[2], animationSpec = tween(300))
        colorAnimation3.animateTo(colors[3], animationSpec = tween(300))
    }
    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            SmallCircle(color = colorAnimation0.value)
            SmallCircle(color = colorAnimation1.value)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            SmallCircle(color = colorAnimation2.value)
            SmallCircle(color = colorAnimation3.value)
        }
    }
}


@Preview
@Composable
fun FeedbackCirclesPreview() {
    FeedbackCircles(colors = listOf(Color.Cyan, Color.Magenta, Color.Green, Color.Blue))
}
