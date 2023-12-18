package com.example.masterand.GameScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FeedbackCircles(colors: List<Color>) {
    Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            SmallCircle(color = colors[0])
            SmallCircle(color = colors[1])
        }
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            SmallCircle(color = colors[2])
            SmallCircle(color = colors[3])
        }
    }
}

@Preview
@Composable
fun FeedbackCirclesPreview() {
    FeedbackCircles(colors = listOf(Color.Cyan, Color.Magenta, Color.Green, Color.Blue))
}
