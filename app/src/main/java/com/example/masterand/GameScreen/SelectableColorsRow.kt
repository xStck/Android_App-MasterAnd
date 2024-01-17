package com.example.masterand.GameScreen

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SelectableColorsRow(
    colors: List<Color>,
    onClick: (buttonIndex: Int) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
        for (i in 0..3) {
            val animatedColor by remember { mutableStateOf(true) }
            val animatedColor1 by animateColorAsState(
                if (animatedColor) colors[i] else {
                    if (i == colors.count() - 1) {
                        colors[colors.count() - 2]
                    } else {
                        colors[i + 1]
                    }
                },
                animationSpec = repeatable(
                    iterations = 10,
                    animation = tween(
                        durationMillis = 100,
                        delayMillis = 30,
                        easing = LinearOutSlowInEasing
                    ),
                    repeatMode = RepeatMode.Reverse
                ), label = "color"
            )
            CircularButton(onClick = {
                onClick(i)
                animatedColor != animatedColor
            }, color = animatedColor1)
        }
    }
}

@Preview
@Composable
fun SelectableColorsRowPreview() {
    SelectableColorsRow(
        colors = listOf(Color.Red, Color.Cyan, Color.Green, Color.Blue),
        onClick = {}
    )
}