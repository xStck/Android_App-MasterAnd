package com.example.masterand.GameScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SelectableColorsRow(
    colors: List<Color>,
    onClick: (buttonIndex: Int) -> Unit
) {
    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
        CircularButton(onClick = { onClick(0) }, color = colors[0])
        CircularButton(onClick = { onClick(1) }, color = colors[1])
        CircularButton(onClick = { onClick(2) }, color = colors[2])
        CircularButton(onClick = { onClick(3) }, color = colors[3])
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